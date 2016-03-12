package at.baulu.readies.rest.location;

import at.baulu.readies.rest.transaction.Transaction;

/**
 * Created by Mario on 12.03.2016.
 */
public class TransactionOffer {
    private String transactionId;
    private double amount;

    public TransactionOffer() {
    }

    private TransactionOffer(String transactionId, double amount) {
        this.transactionId = transactionId;
        this.amount = amount;
    }

    public static TransactionOffer fromTransaction(Transaction transaction) {
        return new TransactionOffer(transaction.getTransactionId(), transaction.getAmount());
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
