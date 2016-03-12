package at.baulu.readies.rest.location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mario on 12.03.2016.
 */
public class TransactionSummary {
    private List<TransactionOffer> availableTransactions = new ArrayList<>();
    private List<TransactionOffer> onGoingTransactions = new ArrayList<>();

    public TransactionSummary() {
    }

    public TransactionSummary(List<TransactionOffer> availableTransactions, List<TransactionOffer> onGoingTransactions) {
        this.availableTransactions = availableTransactions;
        this.onGoingTransactions = onGoingTransactions;
    }

    public List<TransactionOffer> getAvailableTransactions() {
        return availableTransactions;
    }

    public void setAvailableTransactions(List<TransactionOffer> availableTransactions) {
        this.availableTransactions = availableTransactions;
    }

    public List<TransactionOffer> getOnGoingTransactions() {
        return onGoingTransactions;
    }

    public void setOnGoingTransactions(List<TransactionOffer> onGoingTransactions) {
        this.onGoingTransactions = onGoingTransactions;
    }
}
