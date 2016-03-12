package at.baulu.readies.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by Mario on 11.03.2016.
 */
@Entity
public class User extends AbstractPersistentEntity {
    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    @Column(nullable = false, unique = true)
    @JsonIgnore
    private String email;
    @Column(nullable = false)
    @JsonIgnore
    private String subAccount;
    @Column(nullable = false)
    private String photoUrl;
    @Column(nullable = false)
    private Double trustScore;

    public User() {
    }

    public User(String firstname, String lastname, String email, String subAccount, String photoUrl, Double trustScroe) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.subAccount = subAccount;
        this.photoUrl = photoUrl;
        this.trustScore = trustScroe;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubAccount() {
        return subAccount;
    }

    public void setSubAccount(String subAccount) {
        this.subAccount = subAccount;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Double getTrustScore() {
        return trustScore;
    }

    public void setTrustScore(Double trustScore) {
        this.trustScore = trustScore;
    }
}
