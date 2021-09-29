package v5.gidd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.mail.javamail.JavaMailSender;
import v5.gidd.entities.activity.Activity;
import v5.gidd.entities.activity.attributes.Location;
import v5.gidd.entities.activity.filter.ActivityFilter;
import v5.gidd.entities.message.Message;
import v5.gidd.entities.message.UpdateActivityModel;
import v5.gidd.entities.message.StrictIndex;
import v5.gidd.repo.ActivityCriteriaRepo;
import v5.gidd.repo.ActivityRepo;
import v5.gidd.repo.WaitlistRepo;

import v5.gidd.entities.equipment.Equipment;
import v5.gidd.entities.message.*;
import v5.gidd.entities.user.User;
import v5.gidd.repo.*;

import v5.gidd.usecase.ActivityInteractor;

import java.util.List;

@org.springframework.stereotype.Service
public class ActivityService extends Service<ActivityRepo> {

    @Autowired private ActivityRepo activityRepo;
    @Autowired private ActivityCriteriaRepo activityCriteriaRepo;
    @Autowired private WaitlistRepo waitlistRepo;
    @Autowired private UserService userService;
    @Autowired private JavaMailSender javaMailSender;
    @Autowired private EquipmentRepo equipmentRepo;

    /**
     * Get activity corresponding to provided index.
     * @param activityId The activity index.
     * @return Message of activity with server status response.
     */


    public Message<Activity> getActivity(StrictIndex activityId){
        return query(activityId, ActivityInteractor::getActivity);
    }

    public Message<GetActivityResponseModel> getSingleActivity(StrictIndex activityId) {
        return query(activityId, ActivityInteractor::getSingleActivity);
    }

    /**
     * Retrieves all registered activities.
     * @return Message of all activities with server status response.
     */

    public Message<List<Activity>> getActivities(){
        return query(ActivityInteractor::getActivities);
    }

    /**
     * Retrieves all organized activities.
     * @return Message of organized activities with server status response.
     */

    public Message<List<Activity>> getOrganizedActivities(){
        return query(userService.getThisUser(), ActivityInteractor::getOrganizedActivities);
    }

    /**
     * Retrieves all enrolled. Organized activities is not considered enrolled.
     * @return Message of enrolled activities with server status response.
     */

    public Message<List<Activity>> getEnrolledActivities(){
        return query(userService.getThisUser(), ActivityInteractor::getEnrolledActivities);
    }

    /**
     * Registers new activity.
     * @param activity A valid activity. Invalid activity will be rejected.
     * @return Message of index to registered activity with server status response.
     */

    public Message<StrictIndex> registerActivity(Activity activity){
        return query(activity, a -> ActivityInteractor.registerActivity(activityRepo, a, userService.getThisUser()));
    }

    /**
     * Alters existing activity. Non-existent activities will be rejected.
     * @param activity Activity which to alter.
     * @return Request status from server.
     */

    public Message<Void> alterActivity(Activity activity) {
        return execute(activity, ActivityInteractor::alterActivity);
    }

    /**
     * Deletes an activity by its index.
     *
     * @param activityId an activity index.
     * @return Request status from server.
     */

    public Message<Void> deleteActivity(StrictIndex activityId){
        return execute(activityId, ActivityInteractor::deleteActivity);
    }

    /**
     * Joins user to activity with corresponding index.
     *
     * @param activityId The corresponding id.
     * @return some user readable textual response.
     */

    public Message<String> joinActivity(StrictIndex activityId) {
        return query(activityId, a -> ActivityInteractor.joinActivity(activityRepo, activityId, waitlistRepo, userService.getThisUser()));
    }

    /**
     * Cancels an owned activity.
     * @param activityId The index of the activity.
     * @return Request status from server.
     */

    public Message<Void> cancelActivity(StrictIndex activityId) {
        return execute(activityId, ActivityInteractor::cancelActivity);
    }

    /**
     * Reactivates an cancelled activity.
     * @param activityId Id of the cancelled activity.
     * @return Request status from server.
     */

    public Message<Void> reactivateActivity(StrictIndex activityId) {
        return execute(activityId, ActivityInteractor::reactivateActivity);
    }

    /**
     * Updates existing activity.
     * @param activityId Id of the activity.
     * @param newActivity Update-model of the target activity.
     * @return Request status from server.
     */


    public Message<Void> updateActivity(StrictIndex activityId, UpdateActivityModel newActivity) {
        return execute(activityId,newActivity, (a,b) -> ActivityInteractor.updateActivity(activityRepo,equipmentRepo, a,b));
    }

    /**
     * Filters activities according to some predicate provided in the filter.
     * @param activityFilter The filter setting the criteria.
     * @return A page consisting of matched activities.
     */

    public Message<Page<Activity>> filterActivities(ActivityFilter activityFilter){
        return query(activityFilter, x -> ActivityInteractor.filterActivities(activityCriteriaRepo, x));
    }

    /**
     * Retrieves all unique locations useful for composing location filtering option list. Note, only locations
     * references by any activity is returned.
     * @return All unique locations which have a activity.
     */

    public Message<List<String>> getUniqueLocations() {
        return query(ActivityInteractor::getUniqueLocations);
    }

    /**
     * Retrieves all unique interests useful for composing interest filtering option list. Note, only interests
     * references by any activity is returned.
     * @return All unique interests which have a activity.
     */

    public Message<List<String>> getUniqueInterests() {
        return query(ActivityInteractor::getUniqueInterests);
    }

    /**
     * Leaves the specified activity-
     * @param activityId the activity id.
     * @return some user readable textual response.
     */

    public Message<String> leaveActivity(StrictIndex activityId) {
        return query(activityId,
                i -> ActivityInteractor.leaveActivity(activityRepo, i, userService, waitlistRepo, javaMailSender));
    }

    /**
     * Get some points from check in
     * @param activityId The activity id from path variable
     * @param location Current user location.
     * @return Request status from server.
     */

    public Message<Void> getPointsFromActivity(StrictIndex activityId, Location location) {
        return execute(activityId,
                location,
                (a,b) -> ActivityInteractor.getPointsFromActivity(activityRepo, activityId, userService, userService.getThisUser(), b));
    }

    @Override
    protected ActivityRepo getRepo() {
        return activityRepo;
    }
}
