package at.baulu.readies.rest.location;

import at.baulu.readies.persistence.entity.User;

import java.util.Date;

/**
 * Created by Mario on 12.03.2016.
 */
public class UserGeoLocation {
    private User user;
    private float longitude;
    private float latitude;
    private Long timeMillis;

    public UserGeoLocation(User userId, float longitude, float latitude) {
        this.user = userId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.timeMillis = new Date().getTime();
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public Long getTimeMillis() {
        return timeMillis;
    }

    public void setTimeMillis(Long timeMillis) {
        this.timeMillis = timeMillis;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User userId) {
        this.user = user;
    }
}
