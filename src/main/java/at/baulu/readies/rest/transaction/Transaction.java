package at.baulu.readies.rest.transaction;

import at.baulu.readies.persistence.entity.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Mario on 12.03.2016.
 */
public class Transaction {
    private final String transactionId;
    private final long startTimeMillis;
    private final User debitor;
    private final double amount;
    private User creditor;
    private List<User> declinedUsers = new ArrayList<>();
    private boolean debitorSubmitted = false;
    private boolean creditorSubmitted = false;

    private Transaction(User debitor, double amount) {
        this.transactionId = UUID.randomUUID().toString();
        this.startTimeMillis = new Date().getTime();
        this.debitor = debitor;
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public long getStartTimeMillis() {
        return startTimeMillis;
    }

    public User getDebitor() {
        return debitor;
    }

    public double getAmount() {
        return amount;
    }

    public User getCreditor() {
        return creditor;
    }

    public void setCreditor(User creditor) {
        this.creditor = creditor;
    }

    public List<User> getDeclinedUsers() {
        return declinedUsers;
    }

    public boolean hasUserDeclinedTransaction(User hasDeclined) {
        if (this.declinedUsers == null || !this.declinedUsers.contains(hasDeclined)) {
            return false;
        }
        return true;
    }

    public void submitForUser(User foundUser) {
        if (this.creditor.equals(foundUser)) {
            this.creditorSubmitted = true;
        } else if (this.debitor.equals(foundUser)) {
            this.debitorSubmitted = true;
        }
    }

    public boolean isDone() {
        return this.creditorSubmitted && this.debitorSubmitted;
    }

    public static class TransactionBuilder {
        private User debitor;
        private double amount;

        public TransactionBuilder withDebitor(User debitor) {
            this.debitor = debitor;
            return this;
        }

        public TransactionBuilder withAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public Transaction createTransaction() {
            return new Transaction(debitor, amount);
        }
    }
}
