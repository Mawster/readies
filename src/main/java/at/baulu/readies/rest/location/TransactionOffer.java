package at.baulu.readies.rest.location;

import at.baulu.readies.rest.transaction.Transaction;

/**
 * Created by Mario on 12.03.2016.
 */
public class TransactionOffer {
    private String transactionId;
    private long startTimeMillis;
    private UserGeoLocation debitor;
    private double amount;
    private UserGeoLocation creditor;
    private boolean debitorSubmitted = false;
    private boolean creditorSubmitted = false;

    public TransactionOffer() {
    }

    public static TransactionOffer fromTransaction(Transaction transaction) {
        TransactionOffer newOffer = new TransactionOffer();
        newOffer.amount = transaction.getAmount();
        newOffer.transactionId = transaction.getTransactionId();
        newOffer.creditorSubmitted = transaction.isCreditorSubmitted();
        newOffer.debitorSubmitted = transaction.isDebitorSubmitted();
        return newOffer;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public long getStartTimeMillis() {
        return startTimeMillis;
    }

    public void setStartTimeMillis(long startTimeMillis) {
        this.startTimeMillis = startTimeMillis;
    }

    public UserGeoLocation getDebitor() {
        return debitor;
    }

    public void setDebitor(UserGeoLocation debitor) {
        this.debitor = debitor;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public UserGeoLocation getCreditor() {
        return creditor;
    }

    public void setCreditor(UserGeoLocation creditor) {
        this.creditor = creditor;
    }

    public boolean isDebitorSubmitted() {
        return debitorSubmitted;
    }

    public void setDebitorSubmitted(boolean debitorSubmitted) {
        this.debitorSubmitted = debitorSubmitted;
    }

    public boolean isCreditorSubmitted() {
        return creditorSubmitted;
    }

    public void setCreditorSubmitted(boolean creditorSubmitted) {
        this.creditorSubmitted = creditorSubmitted;
    }
}
