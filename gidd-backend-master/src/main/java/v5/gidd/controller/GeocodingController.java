package v5.gidd.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.http.MediaType.*;

/**
 * Proxy for Nominatim database
 * Could get data directly from front end
 * Example URLs:
 * http://penguin.flagship.casa/?q=Thomas Angells gate 20
 *
 */
@RestController
@RequestMapping("/geocoding")
public class GeocodingController {
    private static final String API_SCHEME = "http";
    private static final String API_HOST = "penguin.flagship.casa";
    private static final int API_PORT = 8080;

    /**
     * Get geocoding data by location name/address
     * @param location string location
     * @return response with json data
     */
    @GetMapping(value = "/{location}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonNode> getGeocodingByLocation(@PathVariable String location) {
        String queryUrl = UriComponentsBuilder.newInstance()
                .scheme(API_SCHEME)
                .host(API_HOST)
                .port(API_PORT)
                .path("/")
                .queryParam("q", location)
                .build()
                .toUriString();

        return new RestTemplate().exchange(queryUrl, HttpMethod.GET, new HttpEntity<JsonNode>(new HttpHeaders()), JsonNode.class);
    }

    /**
     * Get reverse geocoding data by longitude and latitude
     * @param longitude longitude
     * @param latitude latitude
     * @return response with json data
     */
    @GetMapping(value = "/{longitude}/{latitude}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonNode> getReverseGeocodingByLongitudeAndLatitude(@PathVariable double longitude, @PathVariable double latitude) {
        String queryUrl = UriComponentsBuilder.newInstance()
                .scheme(API_SCHEME)
                .host(API_HOST)
                .port(API_PORT)
                .path("/")
                .queryParam("q", String.format("%s,%s", longitude, latitude))
                .build()
                .toUriString();

        return new RestTemplate().exchange(queryUrl, HttpMethod.GET, new HttpEntity<JsonNode>(new HttpHeaders()), JsonNode.class);
    }
}
