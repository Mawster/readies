package at.baulu.readies.service;

import at.baulu.readies.persistence.entity.User;
import at.baulu.readies.persistence.entity.UserBuilder;
import at.baulu.readies.persistence.repository.UserRepository;
import at.baulu.readies.realex.RealexCrawler;
import at.baulu.readies.rest.registration.LoginRequest;
import at.baulu.readies.rest.registration.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Mario on 11.03.2016.
 */
@Service
public class UserService {
    private UserRepository userRepository;
    private RealexCrawler realexCrawler;

    @Autowired
    public UserService(UserRepository userRepository, RealexCrawler realexCrawler) {
        this.userRepository = userRepository;
        this.realexCrawler = realexCrawler;
    }

    public User createNewUserFromRegistrationRequest(RegistrationRequest registrationRequest) {
        UserBuilder userBuilder = new UserBuilder();
        userBuilder.withEmail(registrationRequest.getEmail());
        userBuilder.withFirstname(registrationRequest.getFirstname());
        userBuilder.withLastname(registrationRequest.getLastname());
        userBuilder.withTrustScroe(registrationRequest.getTrustScore());
        userBuilder.withPhotoUrl(registrationRequest.getPhotoUrl());
        userBuilder.withSubAccount(this.realexCrawler.getNextSubAccount());
        return this.userRepository.save(userBuilder.createUser());
    }

    public User findUserByLoginRequest(LoginRequest loginRequest) {
        User foundUser = this.userRepository.findByEmail(loginRequest.getEmail());
        return foundUser;
    }
}
