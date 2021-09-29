package v5.gidd.entities.activity.waitList;

import v5.gidd.entities.Inspectable;
import v5.gidd.entities.activity.Activity;
import v5.gidd.entities.user.User;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Robin Vold
 * @author Sergio Martinez
 * */

@Entity(name = "ActivityWaitList")
@Table(name = "activity_waitlist")
public class ActivityWaitList implements Inspectable {

    @EmbeddedId
    private WaitlistId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("activityId")
    private Activity activity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @Column(name = "position")
    private Long position;

    public ActivityWaitList(Activity activity, User user, Long position) {
        this.activity = activity;
        this.user = user;
        this.id = new WaitlistId(activity.getId(),user.getId());
        this.position = position;
    }

    public ActivityWaitList() {}

    public WaitlistId getId() {
        return id;
    }

    public void setId(WaitlistId id) {
        this.id = id;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityWaitList that = (ActivityWaitList) o;
        return getId().equals(that.getId()) &&
                getActivity().equals(that.getActivity()) &&
                getUser().equals(that.getUser()) &&
                getPosition().equals(that.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getActivity(), getUser(), getPosition());
    }

    @Override
    public void inspect() {

    }
}
