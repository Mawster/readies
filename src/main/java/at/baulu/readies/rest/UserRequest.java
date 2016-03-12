package at.baulu.readies.rest;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Mario on 12.03.2016.
 */
public class UserRequest {
    @NotBlank
    private String userId;

    public UserRequest() {
    }

    public UserRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
