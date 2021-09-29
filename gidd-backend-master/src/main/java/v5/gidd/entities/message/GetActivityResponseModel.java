package v5.gidd.entities.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import v5.gidd.entities.activity.attributes.ActivityLevel;
import v5.gidd.entities.activity.attributes.Location;

import java.time.LocalDateTime;
import java.util.List;

public class GetActivityResponseModel {
    private Long id;
    private String name;
    private String description;
    private User creator;
    private Location location;
    private int capacity;
    private List<Equipment> equipment;
    private List<User> enrolledUsers;
    private ActivityLevel activityLevel;
    private List<String> interests;
    @JsonFormat(locale = "no", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;
    @JsonFormat(locale = "no", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    public GetActivityResponseModel() {
    }

    public GetActivityResponseModel(Long id, String name, String description, User creator, Location location, int capacity, List<Equipment> equipment, List<User> enrolledUsers, ActivityLevel activityLevel, List<String> interests, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.location = location;
        this.capacity = capacity;
        this.equipment = equipment;
        this.enrolledUsers = enrolledUsers;
        this.activityLevel = activityLevel;
        this.interests = interests;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
    }

    public List<User> getEnrolledUsers() {
        return enrolledUsers;
    }

    public void setEnrolledUsers(List<User> enrolledUsers) {
        this.enrolledUsers = enrolledUsers;
    }

    public ActivityLevel getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(ActivityLevel activityLevel) {
        this.activityLevel = activityLevel;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public static class Equipment {
        private Long id;
        private String name;
        private Long activity;
        private Long claimedByUser;

        public Equipment() {
        }

        public Equipment(Long id, String name, Long activity, Long claimedByUser) {
            this.id = id;
            this.name = name;
            this.activity = activity;
            this.claimedByUser = claimedByUser;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getActivity() {
            return activity;
        }

        public void setActivity(Long activity) {
            this.activity = activity;
        }

        public Long getClaimedByUser() {
            return claimedByUser;
        }

        public void setClaimedByUser(Long claimedByUser) {
            this.claimedByUser = claimedByUser;
        }
    }

    public static class User {
        private Long id;
        private String firstName;
        private String surName;

        public User() {
        }

        public User(Long id, String firstName, String surName) {
            this.id = id;
            this.firstName = firstName;
            this.surName = surName;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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
    }
}
