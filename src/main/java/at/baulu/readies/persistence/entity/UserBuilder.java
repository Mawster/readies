package at.baulu.readies.persistence.entity;

public class UserBuilder {
    private String firstname;
    private String lastname;
    private String email;
    private String subAccount;
    private String photoUrl;
    private Double trustScore;

    public UserBuilder withFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public UserBuilder withLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withSubAccount(String subAccount) {
        this.subAccount = subAccount;
        return this;
    }

    public UserBuilder withPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    public UserBuilder withTrustScroe(Double trustScroe) {
        this.trustScore = trustScroe;
        return this;
    }

    public User createUser() {
        return new User(firstname, lastname, email, subAccount, photoUrl, trustScore);
    }
}