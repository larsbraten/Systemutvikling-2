package v5.gidd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import v5.gidd.entities.activity.Activity;
import v5.gidd.entities.activity.attributes.Location;

import v5.gidd.entities.activity.filter.ActivityFilter;
import v5.gidd.entities.message.Message;
import v5.gidd.entities.message.UpdateActivityModel;
import v5.gidd.entities.message.StrictIndex;
import v5.gidd.entities.equipment.Equipment;
import v5.gidd.entities.message.*;
import v5.gidd.entities.user.User;

import v5.gidd.service.ActivityService;
import v5.gidd.service.EquipmentService;
import v5.gidd.service.UserService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Contains endpoints responsible for activity interactions. Controller, i.e. the superclass,
 * is responsible for formatting http-responses with whatever status-code the service yielded in the payload.
 * Please refer to service doc for more in depth detail.
 */

@RestController
public class ActivityController extends Controller<ActivityService> {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private EquipmentService equipmentService;


    /**
     * Retrieves a specific activity by it's id.
     * @param id The identifier of the activity.
     * @return The activity. Alternatively a error message followed by null payload if not found.
     */


    @GetMapping(value = "/activity/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Message<Activity>> getActivity(@PathVariable long id) {
        return mapRequest(new StrictIndex(id), activityService::getActivity);
    }


    @GetMapping(value = "/activities/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Message<GetActivityResponseModel>> getSingleActivity(@PathVariable long id) {
        return mapRequest(new StrictIndex(id), activityService::getSingleActivity);
    }

    /**
     * Retrieves all registered activities.
     * @return List of activities.
     */

    @GetMapping(value = "/activity", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Message<List<Activity>>> getActivities() {
        return mapRequest(activityService::getActivities);
    }

    /**
     * Retrieves all enrolled. Organized activities is not considered enrolled.
     * @return All enrolled activities.
     */

    @GetMapping(value = "/activities/enrolled", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Message<List<Activity>>> getEnrolledActivities() {
        return mapRequest(activityService::getEnrolledActivities);
    }

    /**
     * Retrieves all organized activities.
     * @return Enrolled activities.
     */

    @GetMapping(value = "/activities/organized", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Message<List<Activity>>> getOrganizedActivities() {
        return mapRequest(activityService::getOrganizedActivities);
    }

    /**
     * Registers new activity.
     * @param activity A valid activity. Invalid activity will be rejected in service layer.
     * @return Index of created activity.
     */

    @PostMapping("/activity")
    public ResponseEntity<Message<StrictIndex>> createActivity(@RequestBody Activity activity) {
        return mapRequest(activity, activityService::registerActivity);
    }

    /**
     * Alters existing activity. Non-existent activities will be rejected.
     * @param activity Activity which to alter.
     * @return Request status from server.
     */

    @PutMapping(value = "/activity", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Message<Void>> alterActivity(@RequestBody Activity activity) {
        return mapRequest(activity, activityService::alterActivity);
    }

    /**
     * Endpoint for deleting an activity
     *
     * @param activityId an IDTransmission with the activity id
     * @return Request status from server.
     */
    @DeleteMapping(value = "/activity/{activityId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Message<Void>> deleteActivity(@PathVariable int activityId) {
        return mapRequest(new StrictIndex(activityId), activityService::deleteActivity);
    }

    /**
     * Endpoint for cancelling an activity
     *
     * @param activityId an IDTransmission with the activity id
     * @return Request status from server.
     */
    @PutMapping(value = "/activity/{activityId}/cancel", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Message<Void>> cancelActivity(@PathVariable int activityId) {
        return mapRequest(new StrictIndex(activityId), activityService::cancelActivity);
    }

    /**
     * Endpoint for reactivating an activity
     *
     * @param activityId an IDTransmission with the activity id
     * @return Request status from server.
     */
    @PutMapping(value = "/activity/{activityId}/reactivate", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Message<Void>> reactivateActivity(@PathVariable int activityId) {
        return mapRequest(new StrictIndex(activityId), activityService::reactivateActivity);
    }

    /**
     * Endpoint for joining an activity
     *
     * @param activityId The corresponding id.
     * @return some user readable textual response.
     */
    @PostMapping(value = "/activities/{activityId}/join", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Message<String>> joinActivity(@PathVariable int activityId) {
        return mapRequest(new StrictIndex(activityId), activityService::joinActivity);
    }

    /**
     * Filters activities according to specified predicates. Predicate candidates of same field is always
     * OR'ed during filtering, while distinct fields are AND'ed.
     * @param activityFilter Predicated for filtering process.
     * @return Page containing meta-information of result, and the result itself.
     */

    @PostMapping(value = "/activities/filter", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Message<Page<Activity>>> filterActivities(@RequestBody ActivityFilter activityFilter){
        return mapRequest(activityFilter, activityService::filterActivities);
    }

    /**
     * Retrieves all unique locations useful for composing location filtering option list. Note, only locations
     * references by any activity is returned.
     * @return All unique locations which have a activity.
     */

    @GetMapping(value = "/activities/locations/unique", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Message<List<String>>> getUniqueLocations(){
        return mapRequest(activityService::getUniqueLocations);
    }

    /**
     * Retrieves all unique interests useful for composing interest filtering option list. Note, only interests
     * references by any activity is returned.
     * @return All unique interests which have a activity.
     */

    @GetMapping(value = "/activities/interests/unique", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Message<List<String>>> getUniqueInterests() {
        return mapRequest(activityService::getUniqueInterests);
    }

    /**
     * Endpoint for leaving an activity
     * @param activityId the activity id from path variable
     * @return some user readable textual response.
     */

    @DeleteMapping(value = "/activities/{activityId}/join", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Message<String>> leaveActivity(@PathVariable int activityId) {
        return mapRequest(new StrictIndex(activityId), activityService::leaveActivity);
    }

    /**
     * Get some points from check in
     * @param activityId The activity id from path variable
     * @param location Current user location.
     * @return Request status from server.
     */

    @PostMapping(value = "/activities/{activityId}/checkin", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Message<Void>> getPointsFromActivity(@PathVariable int activityId, @RequestBody Location location) {
        return mapRequest(new StrictIndex(activityId), location, activityService::getPointsFromActivity);
    }

    /**
     * Update activity by activity index and model.
     * @param activityId Id of activity to update.
     * @param newActivity Model of altered activity.
     * @return Request status from server.
     */

    //TODO use alter activity instead?

    @PutMapping(value = "/activity/{activityId}/update", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Message<Void>> updateActivity(@PathVariable long activityId, @RequestBody UpdateActivityModel newActivity) {
        return mapRequest(new StrictIndex(activityId), newActivity, activityService::updateActivity);
    }

    @Override
    protected ActivityService getService() {
        return activityService;
    }
}
