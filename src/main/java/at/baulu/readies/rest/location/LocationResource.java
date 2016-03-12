package at.baulu.readies.rest.location;

import at.baulu.readies.ReadiesPlatform;
import at.baulu.readies.persistence.entity.User;
import at.baulu.readies.persistence.repository.UserRepository;
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
import java.util.Collection;

/**
 * Created by Mario on 11.03.2016.
 */
@RestController
@RequestMapping("/location")
public class LocationResource {
    private static final Log LOG = LogFactory.getLog(LocationResource.class);
    private UserRepository userRepository;
    private ReadiesPlatform readiesPlatform;

    @Autowired
    public LocationResource(UserRepository userRepository, ReadiesPlatform readiesPlatform) {
        this.userRepository = userRepository;
        this.readiesPlatform = readiesPlatform;
    }

    @RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TransactionSummary> updateLocation(@RequestBody @Valid LocationRequest locationRequest) {
        LOG.info(String.format("Updating the location of an user %s !", ToStringBuilder.reflectionToString
                (locationRequest, ToStringStyle.JSON_STYLE)));
        User foundUser = this.userRepository.findOne(locationRequest.getUserId());
        if (foundUser == null) {
            LOG.info("User not found!");
            return new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
        }
        this.readiesPlatform.updateUserLocationFromRequest(locationRequest);
        LOG.info("User location updated - reading transaction summary!");
        return new ResponseEntity<TransactionSummary>(this.readiesPlatform.getTranscationsForUser(foundUser), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Collection<UserGeoLocation>> getLocations() {
        LOG.info("Reading all locations of the users!");
        return new ResponseEntity<Collection<UserGeoLocation>>(this.readiesPlatform.getUserPositions(), HttpStatus.OK);
    }
}
