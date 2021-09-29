package v5.gidd.entities.message;

import v5.gidd.entities.Inspectable;
import v5.gidd.throwable.GiddException;

import java.util.Objects;

/**
 * Wrapper for index sent from client. Reasons for this wrapper is due to requirement of Inspectable's
 * to be used in service. This ensures that data sent from client is validated before further processing is done.
 */

public class StrictIndex implements Inspectable {

    private long requestId;

    public StrictIndex(long id) {
        this.requestId = id;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    @Override
    public void inspect() {
        if(requestId < 0){
            throw new GiddException(Status.INVALID_INPUT, "Id's cannot be negative");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StrictIndex that = (StrictIndex) o;
        return requestId == that.requestId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId);
    }
}
