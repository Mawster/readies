package at.baulu.readies;

import at.baulu.readies.persistence.entity.User;
import at.baulu.readies.realex.ReadiesRealexClient;
import at.baulu.readies.rest.location.LocationRequest;
import at.baulu.readies.rest.location.TransactionOffer;
import at.baulu.readies.rest.location.TransactionSummary;
import at.baulu.readies.rest.location.UserGeoLocation;
import at.baulu.readies.rest.transaction.Transaction;
import at.baulu.readies.rest.transaction.TransactionRequest;
import at.baulu.readies.utils.HaversineAlgorithm;
import com.realexpayments.remote.sdk.domain.payment.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Mario on 12.03.2016.
 */
@Component
public class ReadiesPlatform {

    private final double HUMAN_WALK_SPEED_KM_HOUR = 0.417;
    private Map<String, UserGeoLocation> userPositions;
    private Map<User, List<Transaction>> ongoingTransactions;
    private List<Transaction> activeTransactions;
    private List<Transaction> finishedTransactions;
    private ReadiesRealexClient readiesRealexClient;

    @Autowired
    public ReadiesPlatform(ReadiesRealexClient readiesRealexClient) {
        this.readiesRealexClient = readiesRealexClient;
        this.userPositions = new HashMap<>();
        this.activeTransactions = new ArrayList<>();
        this.finishedTransactions = new ArrayList<>();
        this.ongoingTransactions = new HashMap<>();
    }

    public Collection<UserGeoLocation> getUserPositions() {
        return this.userPositions.values();
    }

    public void updateUserLocationFromRequest(LocationRequest locationRequest) {
        UserGeoLocation newPosition = new UserGeoLocation(locationRequest.getUserId(), locationRequest.getLongitude(),
                locationRequest
                        .getLatitude());
        this.userPositions.put(locationRequest.getUserId(), newPosition);
    }

    public Transaction createTransactionForUserFromRequest(User debitor, TransactionRequest transactionRequest) {
        Transaction.TransactionBuilder transactionBuilder = new Transaction.TransactionBuilder();
        transactionBuilder.withAmount(transactionRequest.getAmount());
        transactionBuilder.withDebitor(debitor);
        Transaction createdTransaction = transactionBuilder.createTransaction();
        this.activeTransactions.add(createdTransaction);
        return createdTransaction;
    }

    public boolean acceptTransactionForUser(String transactionId, User foundUser) {
        synchronized (this.activeTransactions) {
            Transaction foundTransaction = null;
            for (Transaction transaction : this.activeTransactions) {
                if (transaction.getTransactionId().equals(transactionId) && transaction.getDebitor() != foundUser) {
                    if (transaction.getCreditor() == null) {
                        transaction.setCreditor(foundUser);
                        foundTransaction = transaction;
                        this.addTransactionToOngoing(transaction.getCreditor(), foundTransaction);
                        this.addTransactionToOngoing(transaction.getDebitor(), foundTransaction);
                    }
                }
            }
            if (foundTransaction != null && foundTransaction.getCreditor() != null) {
                this.activeTransactions.remove(foundTransaction);
                return true;
            }
            return false;
        }
    }

    private void addTransactionToOngoing(User transactionsFor, Transaction foundTransaction) {
        List<Transaction> onGoingTransactionsOfUser = this.ongoingTransactions.get(transactionsFor);
        if (onGoingTransactionsOfUser == null) {
            List<Transaction> transactions = new ArrayList<>();
            transactions.add(foundTransaction);
            this.ongoingTransactions.put(transactionsFor, transactions);
        } else {
            onGoingTransactionsOfUser.add(foundTransaction);
        }
    }

    public void declineTransactionForUser(String transactionId, User foundUser) {
        synchronized (this.activeTransactions) {
            for (Transaction transaction : this.activeTransactions) {
                if (transaction.getTransactionId().equals(transactionId)) {
                    transaction.getDeclinedUsers().add(foundUser);
                    break;
                }
            }
        }
    }

    public TransactionSummary getTranscationsForUser(User foundUser) {
        TransactionSummary transactionSummary = new TransactionSummary();

        List<TransactionOffer> transactionOffers = new ArrayList<>();
        synchronized (this.activeTransactions) {
            UserGeoLocation actualUserLocation = this.userPositions.get(foundUser.getId());
            if (actualUserLocation != null) {
                for (Transaction transaction : this.activeTransactions) {
                    UserGeoLocation debitorLocation = this.userPositions.get(transaction.getDebitor().getId());
                    if (debitorLocation == null) {
                        continue;
                    }
                    if (foundUser.equals(transaction.getDebitor())) {
                        continue;
                    }
                    if (transaction.getDeclinedUsers().contains(foundUser)) {
                        continue;
                    }
                    double distanceInKM = HaversineAlgorithm.haversine(actualUserLocation.getLatitude(), actualUserLocation
                                    .getLongitude(),
                            debitorLocation.getLatitude(), debitorLocation.getLongitude());
                    if (distanceInKM < HUMAN_WALK_SPEED_KM_HOUR) {
                        transactionOffers.add(TransactionOffer.fromTransaction(transaction));
                    }
                }
            }
        }
        transactionSummary.setAvailableTransactions(transactionOffers);

        List<TransactionOffer> ongoingTransactionOffers = new ArrayList<>();
        synchronized (this.ongoingTransactions) {
            List<Transaction> ongoingTransactionsOfUser = this.ongoingTransactions.get(foundUser);
            if (ongoingTransactionsOfUser != null) {
                for (Transaction transaction : ongoingTransactionsOfUser) {
                    ongoingTransactionOffers.add(TransactionOffer.fromTransaction(transaction));
                }
            }
        }
        transactionSummary.setOnGoingTransactions(ongoingTransactionOffers);
        return transactionSummary;
    }

    public boolean submitTransactionForUser(String transactionId, User foundUser) {
        List<Transaction> ongoingTransactionsForUser = this.ongoingTransactions.get(foundUser);
        if (ongoingTransactionsForUser != null) {
            Transaction foundTransaction = null;
            synchronized (ongoingTransactionsForUser) {
                for (Transaction transaction : ongoingTransactionsForUser) {
                    if (transaction.getTransactionId().equals(transactionId)) {
                        foundTransaction = transaction;
                        transaction.submitForUser(foundUser);
                    }
                }
            }
            if (foundTransaction != null && foundTransaction.isDone()) {
                PaymentResponse response = readiesRealexClient.executePayment(foundTransaction);
                this.ongoingTransactions.get(foundTransaction.getDebitor()).remove(foundTransaction);
                this.ongoingTransactions.get(foundTransaction.getCreditor()).remove(foundTransaction);
                if (response.isSuccess()) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}
