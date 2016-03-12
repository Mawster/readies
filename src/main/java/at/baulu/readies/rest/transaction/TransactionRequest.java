package at.baulu.readies.rest.transaction;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Mario on 12.03.2016.
 */
public class TransactionRequest {
    @NotBlank
    private String userId;
    private double amount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
