package at.baulu.readies.rest.transaction;

import at.baulu.readies.ReadiesPlatform;
import at.baulu.readies.persistence.entity.User;
import at.baulu.readies.persistence.repository.UserRepository;
import at.baulu.readies.rest.UserRequest;
import at.baulu.readies.rest.UserResponse;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Mario on 12.03.2016.
 */
@RestController
@RequestMapping("/transaction")
public class TransactionResource {
    private static final Log LOG = LogFactory.getLog(TransactionResource.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReadiesPlatform readiesPlatform;

    @RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity registerNewTransaction(@RequestBody @Valid TransactionRequest transactionRequest) {
        LOG.info(String.format("Try to create a new transaction %s !", ToStringBuilder.reflectionToString
                (transactionRequest, ToStringStyle.JSON_STYLE)));
        User foundUser = this.userRepository.findOne(transactionRequest.getUserId());
        if (foundUser == null) {
            LOG.info("User not found!");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        String transactionId = this.readiesPlatform.createTransactionForUserFromRequest(foundUser,
                transactionRequest).getTransactionId();
        LOG.info(String.format("Transaction created %s !", transactionId));
        return new ResponseEntity<UserResponse>(new UserResponse("Transaction with id: " + transactionId + " " +
                "created!"), HttpStatus
                .OK);
    }

    @RequestMapping(path = "/{transactionId}/accept", method = RequestMethod.POST, produces = {MediaType
            .APPLICATION_JSON_VALUE})
    public ResponseEntity acceptTransaction(@PathVariable String transactionId, @RequestBody @Valid UserRequest
            userRequest) {
        LOG.info(String.format("User %s tries to accept a payment with id %s !", ToStringBuilder.reflectionToString
                (userRequest, ToStringStyle.JSON_STYLE), transactionId));
        User foundUser = this.userRepository.findOne(userRequest.getUserId());
        if (foundUser == null) {
            LOG.info("User not found!");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        boolean couldAccept = this.readiesPlatform.acceptTransactionForUser(transactionId, foundUser);
        if (couldAccept) {
            LOG.info("Transaction could be accepted by the user!");
            return new ResponseEntity<UserResponse>(new UserResponse("Transaction accepted!"), HttpStatus
                    .OK);
        } else {
            LOG.info("Transaction could not be accepted by the user!");
            return new ResponseEntity<UserResponse>(new UserResponse("Transaction couldn't be accepted!"), HttpStatus
                    .NOT_FOUND);
        }
    }

    @RequestMapping(path = "/{transactionId}/decline", method = RequestMethod.POST, produces = {MediaType
            .APPLICATION_JSON_VALUE})
    public ResponseEntity declineTransaction(@PathVariable String transactionId, @RequestBody @Valid UserRequest
            userRequest) {
        LOG.info(String.format("User %s declines a payment with id %s !", ToStringBuilder.reflectionToString
                (userRequest, ToStringStyle.JSON_STYLE), transactionId));
        User foundUser = this.userRepository.findOne(userRequest.getUserId());
        if (foundUser == null) {
            LOG.info("User not found!");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        this.readiesPlatform.declineTransactionForUser(transactionId, foundUser);
        return new ResponseEntity(HttpStatus
                .OK);
    }

    @RequestMapping(path = "/{transactionId}/submit", method = RequestMethod.POST, produces = {MediaType
            .APPLICATION_JSON_VALUE})
    public ResponseEntity submitTransaction(@PathVariable String transactionId, @RequestBody @Valid UserRequest
            userRequest) {
        LOG.info(String.format("User %s wants to submit the payment with id %s !", ToStringBuilder.reflectionToString
                (userRequest, ToStringStyle.JSON_STYLE), transactionId));
        User foundUser = this.userRepository.findOne(userRequest.getUserId());
        if (foundUser == null) {
            LOG.info("User not found!");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        boolean done = this.readiesPlatform.submitTransactionForUser(transactionId, foundUser);
        if (done) {
            LOG.info("The transaction is completely done!");
            return new ResponseEntity<UserResponse>(new UserResponse("The Transaction is completed!"), HttpStatus
                    .OK);
        } else {
            LOG.info("The Transaction is still open!");
            return new ResponseEntity<UserResponse>(new UserResponse("The Transaction is still open - maybe your " +
                    "partner has to submit the payment!"),
                    HttpStatus
                            .OK);
        }
    }
}
