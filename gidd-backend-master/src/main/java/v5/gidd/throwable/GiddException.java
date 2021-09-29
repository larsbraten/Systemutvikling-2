package v5.gidd.throwable;

import v5.gidd.entities.message.Message;
import v5.gidd.entities.message.Status;

/**
 * Used specifically in this application to provide the necessary information to service when some data is
 * Invalid, or user requests fails.
 */

public class GiddException extends RuntimeException{

    /**
     * Predefined error message.
     */
    private final Status status;

    /**
     * User readable description.
     */
    private final String description;

    public GiddException(Status status, String description){
        this.status = status;
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Converts exception to transmittable message with appropriate header.
     * @param <T> Type of payload. The provided payload from fail request will always be null.
     * @return The message with descriptive error message.
     */

    public <T> Message<T> toMessage(){
        return new Message<>(status, description, null);
    }
}
