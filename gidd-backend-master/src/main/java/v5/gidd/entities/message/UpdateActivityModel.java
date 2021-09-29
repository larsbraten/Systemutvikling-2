package v5.gidd.entities.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import v5.gidd.entities.Inspectable;
import v5.gidd.entities.activity.attributes.ActivityLevel;
import v5.gidd.entities.activity.attributes.Location;
import v5.gidd.entities.equipment.Equipment;
import v5.gidd.throwable.GiddException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UpdateActivityModel implements Inspectable {

    private String name;
    private String description;
    private Location location;
    private int capacity;
    private ActivityLevel activityLevel;
    private List<String> interests;
    @JsonFormat(locale = "no", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;
    @JsonFormat(locale = "no", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;
    private List<UpdateEquipmentModel> equipment;

    public UpdateActivityModel() {
    }

    public UpdateActivityModel(String name, String description, Location location, int capacity, ActivityLevel activityLevel, List<String> interests, LocalDateTime startTime, LocalDateTime endTime, List<UpdateEquipmentModel> equipment) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.capacity = capacity;
        this.activityLevel = activityLevel;
        this.interests = interests;
        this.startTime = startTime;
        this.endTime = endTime;
        this.equipment = equipment;
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

    public List<UpdateEquipmentModel> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<UpdateEquipmentModel> equipment) {
        this.equipment = equipment;
    }

    @Override
    public void inspect() {
        if (name.equals("")) throw new GiddException(Status.ACTIVITY_INVALID_NAME, "Name is invalid");
        if (Optional.ofNullable(location).isEmpty())
            throw new GiddException(Status.ACTIVITY_INVALID_LOCATION, "Location is missing");
        if (capacity < 0) throw new GiddException(Status.ACTIVITY_INVALID_CAPACITY, "Capacity can not be negative");
        if (startTime.isBefore(LocalDateTime.now()))
            throw new GiddException(Status.ACTIVITY_INVALID_TIME, "Start time cannot be back in time");
        if (startTime.isAfter(endTime))
            throw new GiddException(Status.ACTIVITY_INVALID_TIME, "Start time cannot be after the end time");
    }
}
