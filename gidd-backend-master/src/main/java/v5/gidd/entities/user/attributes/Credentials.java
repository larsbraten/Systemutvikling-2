package v5.gidd.entities.user.attributes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import v5.gidd.entities.Inspectable;
import v5.gidd.entities.message.Status;
import v5.gidd.throwable.GiddException;

import javax.persistence.Embeddable;

/**
 * Data class for credentials. Implements inspectable to ensure valid data.
 */

@Embeddable
public class Credentials implements Inspectable {

    private String email;

    @JsonIgnore
    private String password;

    public Credentials(){
    }

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    //TODO Add better pattern
    @Override
    public void inspect(){
        if(!email.matches("[\\w.]*@[\\w.]*")) throw new GiddException(Status.INVALID_USERNAME, "Email is invalid");
        if (email.equals(password)) throw new GiddException(Status.INVALID_PASSWORD, "Password cannot be same as email");
        if(!password.matches(".*")) throw new GiddException(Status.INVALID_PASSWORD, "Password is invalid");
        if (password.length() < 7) throw new GiddException(Status.INVALID_PASSWORD, "Password must be atleast 7 characters");
    }
}
