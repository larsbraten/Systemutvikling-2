package v5.gidd.entities.activity.attributes;

import v5.gidd.entities.Inspectable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

/**
 * Embeddable Location object. Stores location, country, city, longitude and latitude data.
 *
 * @author Karl Labrador
 * @author Lars-Håvard Holter Bråten
 */
@Embeddable
public class Location implements Inspectable {
    @Column(name = "location_place")
    private String place;

    @Column(name = "location_country")
    private String country;

    @Column(name = "location_city")
    private String city;

    @Column(name = "location_longitude")
    private double longitude;

    @Column(name = "location_latitude")
    private double latitude;

    public Location() {
    }

    /**
     * Location constructor
     *
     * @param place     location
     * @param country   country
     * @param city      city
     * @param longitude longitude
     * @param latitude  latitude
     */
    public Location(String place, String country, String city, double longitude, double latitude) {
        this.place = place;
        this.country = country;
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * Location constructor
     *
     * @param place   location
     * @param country country
     * @param city    city
     */
    public Location(String place, String country, String city) {
        this(place, country, city, 0.00, 0.00);
    }

    /**
     * Location constructor
     *
     * @param place location
     * @param city  city
     */
    public Location(String place, String city) {
        this(place, "", city, 0.00, 0.00);
    }

    /**
     * Location constructor
     *
     * @param place location
     */
    public Location(String place) {
        this(place, "", "", 0.00, 0.00);
    }

    /**
     * Location constructor
     *
     * @param longitude longitude
     * @param latitude  latitude
     */
    public Location(double longitude, double latitude) {
        this("", "", "", longitude, latitude);
    }

    /**
     * Get method for place
     *
     * @return String place
     */
    public String getPlace() {
        return place;
    }

    /**
     * Set method for place
     *
     * @param place location
     */
    public void setPlace(String place) {
        this.place = place;
    }

    /**
     * Get method for country
     *
     * @return String country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Set method for country
     *
     * @param country country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Get method for city
     *
     * @return String city
     */
    public String getCity() {
        return city;
    }

    /**
     * Set method for city
     *
     * @param city city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Get method for longitude
     *
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Set method for longitude
     *
     * @param longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Get method for latitude
     *
     * @return latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Set method for latitude
     *
     * @param latitude latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double distance (Location target) {
        double R = 6378.173;

        double dLat = (target.getLatitude() * Math.PI) / 180 - (this.latitude * Math.PI) / 180;
        double dLon = (target.getLongitude() * Math.PI) / 180 - (this.longitude * Math.PI) / 180;
        double a =
                Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                        Math.cos((this.latitude * Math.PI) / 180) * Math.cos((target.getLatitude() * Math.PI) / 180) *
                                Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (R * c) * 1000;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(getCountry(), location.getCountry()) && Objects.equals(getCity(), location.getCity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountry(), getCity());
    }

    @Override
    public void inspect() {

    }
}
