package at.baulu.readies.rest.quantum;

import at.baulu.readies.rest.UserRequest;
import com.postquantum.pqcheck.clientlib.PQCheckClient;
import com.postquantum.pqcheck.clientlib.response.Authorisation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * Created by Mario on 12.03.2016.
 */
@RestController
@RequestMapping("/quantum")
public class QuantumResource {
    @Value("${readies.postquantum.uuid-secret}")
    private String uuid;
    @Value("${readies.postquantum.namespace-secret}")
    private String namespace;
    @Value("${readies.postquantum.secret-secret}")
    private String secret;

    @RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity register(@RequestBody UserRequest userRequest) throws IOException, URISyntaxException {
        PQCheckClient pqCheckClient = new PQCheckClient(URI.create("https://stable-beta-api-pqcheck.post-quantum.com"),
                UUID.fromString(uuid),
                secret);

        Authorisation authorisation = pqCheckClient.createAuthorisation(userRequest.getUserId(), "849", 123412);

        return new ResponseEntity(HttpStatus
                .CREATED);
    }


}
