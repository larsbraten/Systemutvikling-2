package v5.gidd.entities.user.attributes;

import com.fasterxml.jackson.annotation.JsonFormat;
import v5.gidd.entities.Inspectable;
import v5.gidd.entities.message.Status;
import v5.gidd.throwable.GiddException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;

/**
 * Data class for persona. Implements inspectable to ensure valid data.
 */

@Embeddable
public class Persona implements Inspectable {


    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String surName;

    @JsonFormat(locale = "no", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    public Persona() {
    }

    public Persona(String firstName, String surName, LocalDate birthday) {
        this.firstName = firstName;
        this.surName = surName;
        this.birthday = birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public void inspect() {
        if(surName.trim().length() == 0 )
            throw new GiddException(Status.INVALID_LAST_NAME, "Last name cannot be empty.");
        if(firstName.trim().length() == 0 )
            throw new GiddException(Status.INVALID_FIRST_NAME, "First name cannot be empty.");
        if (!firstName.matches("[A-Z][a-z]+"))
            throw new GiddException(Status.INVALID_FIRST_NAME, "First name must begin in upper, succeeded by lower case letters.");
        if(!surName.matches("[A-Z][a-z]+"))
            throw new GiddException(Status.INVALID_LAST_NAME, "Last name must begin in upper, succeeded by lower case letters.");

    }
}
