package at.baulu.readies.rest;

/**
 * Created by Mario on 12.03.2016.
 */
public class UserResponse {
    private String message;

    public UserResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
