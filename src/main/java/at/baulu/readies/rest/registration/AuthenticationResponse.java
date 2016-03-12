package at.baulu.readies.rest.registration;

/**
 * Created by Mario on 11.03.2016.
 */
public class AuthenticationResponse {
    private String userId;

    public AuthenticationResponse(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
