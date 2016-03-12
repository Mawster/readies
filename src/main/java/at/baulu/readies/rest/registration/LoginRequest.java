package at.baulu.readies.rest.registration;

import org.hibernate.validator.constraints.Email;

/**
 * Created by Mario on 11.03.2016.
 */
public class LoginRequest {
    @Email
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
