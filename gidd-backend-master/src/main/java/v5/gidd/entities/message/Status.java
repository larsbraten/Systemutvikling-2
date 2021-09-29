package v5.gidd.entities.message;

import org.springframework.http.HttpStatus;

public enum Status {
    //Whenever nothing went wrong.
    Ok(HttpStatus.OK),

    //Security related
    SERVICE_UNAVAILABLE(HttpStatus.FORBIDDEN),

    //Generic
    INVALID_INPUT(HttpStatus.BAD_REQUEST),

    //User related
    INVALID_USERNAME(HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST),
    INVALID_FIRST_NAME(HttpStatus.FORBIDDEN),
    INVALID_LAST_NAME(HttpStatus.FORBIDDEN),
    INVALID_BIRTHDAY(HttpStatus.FORBIDDEN),
    INVALID_PHONE_NUMBER(HttpStatus.FORBIDDEN),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND),

    //Registration related
    ACCESS_DENIED(HttpStatus.FORBIDDEN),
    REGISTRATION_ERROR(HttpStatus.BAD_REQUEST),
    COULD_NOT_GENERATE_TOKEN(HttpStatus.INTERNAL_SERVER_ERROR),
    MALFORMED_VERIFICATION_TOKEN(HttpStatus.BAD_REQUEST),
    VERIFICATION_TOKEN_IS_EXPIRED(HttpStatus.BAD_REQUEST),
    NON_EXISTENT_TOKEN(HttpStatus.BAD_REQUEST),
    REGISTRATION_SUCCESS(HttpStatus.CREATED),

    //Activity related
    ACTIVITY_NOT_FOUND(HttpStatus.NOT_FOUND),
    MISSING_ACTIVITY_DESCRIPTION(HttpStatus.BAD_REQUEST),
    ACTIVITY_SUCCESS(HttpStatus.OK),
    ACTIVITY_CREATED(HttpStatus.CREATED),
    ACTIVITY_INVALID_NAME(HttpStatus.BAD_REQUEST),
    ACTIVITY_INVALID_LOCATION(HttpStatus.BAD_REQUEST),
    ACTIVITY_INVALID_CAPACITY(HttpStatus.BAD_REQUEST),
    ACTIVITY_INVALID_TIME(HttpStatus.BAD_REQUEST),
    ACTIVITY_ALREADY_ACTIVE(HttpStatus.BAD_REQUEST),
    ACTIVITY_ALREADY_CANCELLED(HttpStatus.BAD_REQUEST),
    ACTIVITY_CAPACITY_EXCEEDED(HttpStatus.BAD_REQUEST),
    ACTIVITY_NOT_ENROLLED(HttpStatus.BAD_REQUEST),
    ACTIVITY_NOT_STARTED(HttpStatus.BAD_REQUEST),
    ACTIVITY_ALREADY_CHECKED_IN(HttpStatus.BAD_REQUEST),
    ACTIVITY_TOO_FAR_AWAY(HttpStatus.BAD_REQUEST),

    // Equipment related
    EQUIPMENT_NOT_FOUND(HttpStatus.NOT_FOUND),
    EQUIPMENT_NOT_ENROLLED(HttpStatus.BAD_REQUEST),
    EQUIPMENT_NOT_CLAIMED(HttpStatus.BAD_REQUEST),
    EQUIPMENT_UNCLAIMABLE(HttpStatus.BAD_REQUEST),
    EQUIPMENT_ALREADY_CLAIMED(HttpStatus.BAD_REQUEST),
    EQUIPMENT_INVALID_NAME(HttpStatus.BAD_REQUEST),

    //Other
    NOT_IMPLEMENTED(HttpStatus.NOT_IMPLEMENTED);


    //TODO more statuses here.

    public final HttpStatus statusCode;

    /**
     * Constructor for these enums. HttpStatus argument is required so controller knows which status
     * the response entity should be set to.
     * @param statusCode The corresponding http response code.
     */

    Status(HttpStatus statusCode){
        this.statusCode = statusCode;
    }
}