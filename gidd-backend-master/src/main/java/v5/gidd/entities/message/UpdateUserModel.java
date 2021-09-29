package v5.gidd.entities.message;

import v5.gidd.entities.Inspectable;
import v5.gidd.entities.user.attributes.Quality;
import v5.gidd.throwable.GiddException;

public class UpdateUserModel implements Inspectable {

    private Quality activityLevel;
    private String password;
    private String phoneNumber;

    public UpdateUserModel() {}

    public UpdateUserModel(Quality activityLevel, String password, String phoneNumber) {
        this.activityLevel = activityLevel;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public Quality getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(Quality activityLevel) {
        this.activityLevel = activityLevel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void inspect() {
        if(this.password != null && !this.password.equals("")){
            if(!password.matches("[\\w.]*")) throw new GiddException(Status.INVALID_PASSWORD, "Password is invalid");
            if (password.length() < 7) throw new GiddException(Status.INVALID_PASSWORD, "Password must be atleast 7 characters");
        }
        if(this.phoneNumber != null && !this.phoneNumber.equals("")){
            if(!phoneNumber.matches("([+]\\d{2})?\\d{8}"))
                throw new GiddException(Status.INVALID_PHONE_NUMBER, "Phone number must be exactly 8 digits long, " +
                        "excluding the option 2 digit nation code.");
        }
    }
}
