package v5.gidd.entities.activity;

import com.fasterxml.jackson.annotation.*;
import v5.gidd.entities.Inspectable;
import v5.gidd.entities.activity.attributes.ActivityIcon;
import v5.gidd.entities.activity.attributes.ActivityLevel;
import v5.gidd.entities.activity.attributes.ActivityStatus;
import v5.gidd.entities.activity.attributes.Location;
import v5.gidd.entities.equipment.Equipment;
import v5.gidd.entities.message.Status;
import v5.gidd.entities.message.StrictIndex;
import v5.gidd.entities.user.User;
import v5.gidd.entities.activity.waitList.ActivityWaitList;
import v5.gidd.throwable.GiddException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Activity model/entity
 *
 * @author Karl Labrador
 * @author Lars-Håvard Holter Bråten
 */
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "activity")
public class Activity implements Inspectable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // bruk IDENTITY hvis db er seeded ved hver kjoering
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

//    @JsonIgnoreProperties(
//            value = {
//                    "enrolledActivities",
//                    "createdActivities",
//                    "id",
//                    "contactInfo",
//                    "credentials",
//                    "activityLevel",
//                    "activityIcon",
//                    "authorities",
//                    "waitListedActivities"
//            } )
    @ManyToOne
    @JoinColumn(
            name="creator_id",
            updatable = false
    )
    @JsonIgnore
    private User creator;

    @Embedded
    private Location location;

    @Size(min = 0)
    @Column(name = "capacity")
    private int capacity;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="activity_id")
    @JsonIgnore
    private List<Equipment> equipment;

//    @JsonIgnoreProperties(
//            value = {
//                    "enrolledActivities",
//                    "createdActivities",
//                    "id",
//                    "contactInfo",
//                    "credentials",
//                    "activityLevel",
//                    "activityIcon",
//                    "authorities",
//                    "waitListedActivities"
//            } )
    @ManyToMany
    @JoinTable(
            name="activity_user",
            joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore
    private List<User> enrolledUsers;

    @NotNull
    @Column(name = "activity_level")
    @Enumerated(value = EnumType.STRING)
    private ActivityLevel activityLevel = ActivityLevel.LOW;

    @NotNull
    @Column(name = "activity_icon")
    @Enumerated(value = EnumType.STRING)
    private ActivityIcon activityIcon = ActivityIcon.RUNNING;

    @ElementCollection
    @CollectionTable(
            name="interest",
            joinColumns = @JoinColumn(name="activity_id")
    )
    @Column(name="interest")
    private List<String> interests;

    @Basic(optional = false)
    @JsonFormat(locale = "no", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Basic(optional = false)
    @JsonFormat(locale = "no", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Basic(optional = false)
    @Column(name = "created", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime created;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private ActivityStatus status = ActivityStatus.ACTIVE;

    @JsonIgnore
    @ManyToMany
    private List<User> checkedInUsers;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<ActivityWaitList> waitListUsers = new ArrayList<>();

    public Activity() {}


    public Activity(String name,
                    Location location,
                    @Size(min = 0) int capacity,
                    String description,
                    User creator,
                    List<Equipment> equipment,
                    @NotNull ActivityLevel activityLevel,
                    @NotNull ActivityIcon activityIcon,
                    List<String> interests,
                    LocalDateTime startTime,
                    LocalDateTime endTime,
                    LocalDateTime created,
                    ActivityStatus status) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.description = description;
        this.creator = creator;
        this.equipment = equipment;
        this.activityLevel = activityLevel;
        this.activityIcon = activityIcon;
        this.interests = interests;
        this.startTime = startTime;
        this.endTime = endTime;
        this.created = created;
        this.status = status;
    }

    public Activity(Long id){
        this.id = id;
    }

    /**
     * Get method for ID
     *
     * @return Long id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set method for ID
     *
     * @param id ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get method for name
     *
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * Set method for name
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get method for location
     *
     * @return Location location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Set method for location
     *
     * @param location location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Get method for capacity
     *
     * @return int capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Set method for capacity
     *
     * @param capacity Activity capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Get method for enrolled users
     *
     * @return List enrolledUsers
     */
    public List<User> getEnrolledUsers() {
        return enrolledUsers;
    }

    /**
     * Set method for enrolled users
     *
     * @param enrolledUsers Enrolled users
     */
    public void setEnrolledUsers(List<User> enrolledUsers) {
        this.enrolledUsers = enrolledUsers;
    }

    /**
     * Get method for activity level
     *
     * @return ActivityLevel activityLevel
     */
    public ActivityLevel getActivityLevel() {
        return activityLevel;
    }

    /**
     * Set method for activity level
     *
     * @param activityLevel Activity level
     */
    public void setActivityLevel(ActivityLevel activityLevel) {
        this.activityLevel = activityLevel;
    }

    /**
     * Get method for activity icon
     *
     * @return ActivityIcon activityIcon
     */
    public ActivityIcon getActivityIcon() {
        return activityIcon;
    }

    /**
     * Set method for activity icon
     *
     * @param activityIcon Activity icon
     */
    public void setActivityIcon(ActivityIcon activityIcon) {
        this.activityIcon = activityIcon;
    }


    /**
     * Get method for interests
     *
     * @return List interests
     */
    public List<String> getInterests() {
        return interests;
    }

    /**
     * Set method for interests
     *
     * @param interests User interests
     */
    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    /**
     * Get method for start time
     *
     * @return LocalDateTime startTime
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Set method for start time
     *
     * @param startTime Activity start time
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Get method for end time
     *
     * @return LocalDateTime endTime
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Set method for end time
     *
     * @param endTime Activity end time
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Get method for created
     *
     * @return LocalDateTime created
     */
    public LocalDateTime getCreated() {
        return created;
    }

    /**
     * Set method for created
     *
     * @param created Activity creation time
     */
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public ActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<Equipment> getEquipment() {
        return equipment;
    }

    @JsonProperty
    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getCheckedInUsers() {
        return checkedInUsers;
    }

    public void setCheckedInUsers(List<User> checkedInUsers) {
        this.checkedInUsers = checkedInUsers;
    }

    public List<ActivityWaitList> getWaitListUsers() {
        return waitListUsers;
    }

    public void setWaitListUsers(List<ActivityWaitList> waitListUsers) {
        this.waitListUsers = waitListUsers;
    }

    /**
     * Enqueues user in wait list if this activity is full.
     * @param user User to enqueue.
     */

    public void addUserToWaitList(User user) {
        ActivityWaitList activityWaitList = new ActivityWaitList(this, user, (long) waitListUsers.size() + 1);
        waitListUsers.add(activityWaitList);
        user.getWaitListedActivities().add(activityWaitList);
    }

    /**
     * Dequeues user when activity is not full.
     * @param user User to dequeue.
     */

    public void removeUserFromWaitList(User user) {
        for (Iterator<ActivityWaitList> iterator = waitListUsers.iterator(); iterator.hasNext();) {
            ActivityWaitList activityWaitList = iterator.next();

            if (activityWaitList.getActivity().equals(this) &&
                    activityWaitList.getUser().equals(user)) {
                iterator.remove();
                activityWaitList.getUser().getWaitListedActivities().remove(activityWaitList);
                activityWaitList.setActivity(null);
                activityWaitList.setUser(null);
            }
        }
    }

    /**
     * Inspects values in service layer to ensure valid data.
     */
    @Override
    public void inspect() {
        if(!name.trim().matches(".{3,}"))
            throw new GiddException(Status.ACTIVITY_INVALID_NAME, "Name cannot be empty, and must be at least 3 characters.");
        Optional.ofNullable(location)
                .orElseThrow(() -> new GiddException(Status.ACTIVITY_INVALID_LOCATION, "Location is missing"));
        if (capacity < 1) throw new GiddException(Status.ACTIVITY_INVALID_CAPACITY, "Capacity must be above zero.");
        if (startTime.isAfter(endTime))
            throw new GiddException(Status.ACTIVITY_INVALID_TIME, "Start time cannot be after the end time");
        Optional.ofNullable(description)
                .orElseThrow(() -> new GiddException(Status.MISSING_ACTIVITY_DESCRIPTION, "Activity description" +
                        "may be empty, but not null."));
    }
}
