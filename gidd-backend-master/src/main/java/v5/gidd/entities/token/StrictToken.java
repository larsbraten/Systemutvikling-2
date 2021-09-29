package v5.gidd.entities.token;

import v5.gidd.entities.Inspectable;
import v5.gidd.entities.message.Status;
import v5.gidd.throwable.GiddException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Used to ensure malformed token from client is rejected.
 */

@Embeddable
public class StrictToken implements Inspectable, Serializable {

    @Column(unique = true, name = "token_key")
    private String key;


    public StrictToken(String key) {
        this.key = key;
    }

    public StrictToken() {}


    public String getKey() {
        return key;
    }

    public void setKey(String token) {
        this.key = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StrictToken that = (StrictToken) o;
        return getKey().equals(that.getKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey());
    }

    @Override
    public void inspect() {
        if(!key.matches("[a-zA-Z0-9-_]{36}"))
            throw new GiddException(Status.MALFORMED_VERIFICATION_TOKEN, "Invalid token key. Must be RFC 4648 compliant" +
                    "and exactly 36 characters long.");
    }
}
