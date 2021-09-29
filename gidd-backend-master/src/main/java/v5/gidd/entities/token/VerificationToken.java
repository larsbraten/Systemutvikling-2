package v5.gidd.entities.token;

import v5.gidd.entities.Inspectable;
import v5.gidd.entities.user.User;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Used to verify user upon registration.
 */

@Entity
@Table(name = "verification_token")
public class VerificationToken implements Inspectable {

    @EmbeddedId
    private StrictToken strictToken;
    @Column
    private LocalDateTime created;
    @Column
    private LocalDateTime expires;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public VerificationToken() {}


    public VerificationToken(User user, Duration expires) {
        this.strictToken = new StrictToken(UUID.randomUUID().toString());
        this.created = LocalDateTime.now();
        this.expires = LocalDateTime.from(this.created).plus(expires);
        this.user = user;
    }

    public StrictToken getStrictToken() {
        return strictToken;
    }

    public void setStrictToken(StrictToken key) {
        this.strictToken = key;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getExpires() {
        return expires;
    }

    public void setExpires(LocalDateTime expires) {
        this.expires = expires;
    }

    public boolean isExpired(){
        return LocalDateTime.now().isAfter(this.expires);
    }

    @Override
    public void inspect() {
        strictToken.inspect();
        user.inspect();
    }
}
