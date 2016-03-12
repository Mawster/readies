package at.baulu.readies.rest.registration;

import at.baulu.readies.persistence.entity.User;
import at.baulu.readies.persistence.repository.UserRepository;
import at.baulu.readies.service.UserService;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by Mario on 11.03.2016.
 */
@RestController
@RequestMapping("/authentication")
public class AuthenticationResource {
    private static final Log LOG = LogFactory.getLog(AuthenticationResource.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path = "/registration", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegistrationRequest registrationRequest) {
        LOG.info(String.format("Try to register a new user %s !", ToStringBuilder.reflectionToString
                (registrationRequest, ToStringStyle.JSON_STYLE)));
        User foundUser = this.userRepository.findByEmail(registrationRequest.getEmail());
        if (foundUser != null) {
            return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(foundUser.getId()), HttpStatus
                    .OK);
        }
        User createdUser = this.userService.createNewUserFromRegistrationRequest(registrationRequest);
        LOG.info(String.format("User %s created!", ToStringBuilder.reflectionToString(createdUser, ToStringStyle.JSON_STYLE)));
        return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(createdUser.getId()), HttpStatus
                .CREATED);
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        LOG.info(String.format("User tries to log in - %s !", ToStringBuilder.reflectionToString(loginRequest, ToStringStyle.JSON_STYLE)));
        User foundUser = this.userService.findUserByLoginRequest(loginRequest);
        if (foundUser == null) {
            LOG.info("User not found!");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(foundUser.getId()), HttpStatus
                .OK);
    }
}
