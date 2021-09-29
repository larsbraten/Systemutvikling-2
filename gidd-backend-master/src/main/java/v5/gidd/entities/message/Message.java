package v5.gidd.entities.message;

//

/**
 * Mandatory wrapper for all data from this agent. Acts like a header in the http-body, providing specific
 * error messages whenever necessary. Http responses will reflect error messages declared here,
 * but is more general and diffuse.
 * @param <T> Type of payload.
 */

public class Message<T> {

    /**
     * Machine readable status field. May change under any transmission due to context.
     */
    private Status status;

    /**
     * Human readable description of status code. Should not be interpreted by any program.
     * That's what the status field is for, as every possible status is predefined, and offers more type safety.
     */
    private String description;

    /**
     * Wrapped payload.
     */
    private T payload;

    public Message(Status status, String description, T payload) {
        this.status = status;
        this.description = description;
        this.payload = payload;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
