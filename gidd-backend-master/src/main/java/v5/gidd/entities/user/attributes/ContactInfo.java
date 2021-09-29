package v5.gidd.entities.user.attributes;

import v5.gidd.entities.Inspectable;
import v5.gidd.entities.message.Status;
import v5.gidd.throwable.GiddException;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Data class for user contact info. Implements inspectable to ensure valid data.
 */

@Embeddable
public class ContactInfo implements Inspectable {

    // todo mapped by email in Credentials
    @Column(insertable = false, updatable = false)
    private String email;

    @Column(name="phone_number")
    private String phoneNumber;

    public ContactInfo() {
    }

    public ContactInfo(String email, String phoneNumber) {
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void inspect() {
        if(!email.matches("[\\w.]*@[\\w.]*"))
            throw new GiddException(Status.INVALID_USERNAME, "Email is invalid");
        if(!phoneNumber.matches("([+]\\d{2})?\\d{8}"))
            throw new GiddException(Status.INVALID_PHONE_NUMBER, "Phone number must be exactly 8 digits long, " +
                    "excluding the optional 2 digit nation code.");
    }
}
