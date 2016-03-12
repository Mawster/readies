package at.baulu.readies.rest.location;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by Mario on 11.03.2016.
 */
public class LocationRequest {
    @NotBlank
    private String userId;
    @NotNull
    private Float longitude;
    @NotNull
    private Float latitude;

    public LocationRequest() {
    }

    public LocationRequest(String userId, Float longitude, Float latitude) {
        this.userId = userId;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
