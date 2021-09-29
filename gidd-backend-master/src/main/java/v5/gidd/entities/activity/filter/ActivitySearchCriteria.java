package v5.gidd.entities.activity.filter;

import v5.gidd.entities.activity.attributes.ActivityLevel;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Search criteria for activity. Client may be specify which activities to fetch by providing candidates in
 * each corresponding field. Null field yields no filtering constraint.
 * If field is set to null, everything will match with it.
 */

public class ActivitySearchCriteria{

    private String name;
    private LocalDateTime startTime;
    private boolean showPreviousActivities;
    private List<String> locations;
    private List<String> interests;
    private List<ActivityLevel> activityLevels;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public boolean showPreviousActivities() {
        return showPreviousActivities;
    }

    public void setShowPreviousActivities(boolean showPreviousActivities) {
        this.showPreviousActivities = showPreviousActivities;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public List<ActivityLevel> getActivityLevels() {
        return activityLevels;
    }

    public void setActivityLevels(List<ActivityLevel> activityLevels) {
        this.activityLevels = activityLevels;
    }

    @Override
    public String toString() {
        return "ActivitySearchCriteria{" +
                "name='" + name + '\'' +
                ", startTime=" + startTime +
                ", locations=" + locations +
                ", interests=" + interests +
                '}';
    }
}
