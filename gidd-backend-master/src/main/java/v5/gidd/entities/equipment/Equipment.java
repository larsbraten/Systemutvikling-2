package v5.gidd.entities.equipment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import v5.gidd.entities.Inspectable;
import v5.gidd.entities.activity.Activity;
import v5.gidd.entities.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "equipment")
public class Equipment implements Inspectable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // bruk IDENTITY hvis db er seeded ved hver kjoering
    private Long id;

    private String name;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(
            name="activity_id"
    )
    @NotNull
    @JsonIgnore
    private Activity activity;

//    @ManyToOne(cascade = CascadeType.DETACH)
@ManyToOne
@JsonIgnore
    private User claimedByUser;

    public Equipment(){
    }

    public Equipment(Long id, String name, Activity activity, User claimedByUser) {
        this.id = id;
        this.name = name;
        this.activity = activity;
        this.claimedByUser = claimedByUser;
    }

    public Equipment(Long id, String name, Activity activity) {
        this.id = id;
        this.name = name;
        this.activity = activity;
    }

    public Equipment(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public User getClaimedByUser() {
        return claimedByUser;
    }

    public void setClaimedByUser(User claimedByUser) {
        this.claimedByUser = claimedByUser;
    }

    @Override
    public void inspect() {

    }
}
