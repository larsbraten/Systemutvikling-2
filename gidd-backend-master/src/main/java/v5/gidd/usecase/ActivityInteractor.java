package v5.gidd.usecase;


import org.springframework.data.domain.Page;
import org.springframework.mail.javamail.JavaMailSender;
import v5.gidd.entities.activity.Activity;
import v5.gidd.entities.activity.attributes.ActivityStatus;
import v5.gidd.entities.activity.attributes.Location;
import v5.gidd.entities.activity.filter.ActivityFilter;

import v5.gidd.entities.activity.waitList.ActivityWaitList;
import v5.gidd.entities.message.Status;
import v5.gidd.entities.message.UpdateActivityModel;

import v5.gidd.entities.message.StrictIndex;
import v5.gidd.entities.user.User;
import v5.gidd.repo.ActivityCriteriaRepo;
import v5.gidd.repo.ActivityRepo;
import v5.gidd.repo.UserRepo;
import v5.gidd.repo.WaitlistRepo;
import v5.gidd.service.UserService;

import v5.gidd.entities.equipment.Equipment;
import v5.gidd.entities.message.*;
import v5.gidd.entities.user.User;
import v5.gidd.repo.*;

import v5.gidd.throwable.GiddException;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ActivityInteractor {

    /**
     * Inspects an Activity object and saves it to repo if no exceptions
     * @param activityRepo activity repo
     * @param activity activity object
     */
    public static StrictIndex registerActivity(ActivityRepo activityRepo, Activity activity, User user){
        // Verify
        activity.inspect();
        activity.setCreator(user);

        // Save
        Activity savedActivity = activityRepo.save(activity);

        // Return id for activity
        return new StrictIndex(savedActivity.getId());
    }

    public static void deleteActivity(ActivityRepo activityRepo, StrictIndex activityId){
        Activity activity = getActivity(activityRepo, activityId);

        activityRepo.delete(activity);
    }

    /**
     * Cancels an activity by changing its status to cancelled
     * @param activityRepo dependency injection, activity repo
     * @param activityId an IdTransmission object with the activity id
     */
    public static void cancelActivity(ActivityRepo activityRepo, StrictIndex activityId) {
        Activity activity = getActivity(activityRepo, activityId);

        // Check if already canceled?
        if (activity.getStatus() == ActivityStatus.CANCELLED) {
            throw new GiddException(Status.ACTIVITY_ALREADY_CANCELLED, "Activity is already cancelled");
        }

        activity.setStatus(ActivityStatus.CANCELLED);
        activityRepo.save(activity);
    }

    /**
     * Reactivates a cancelled activity by changing its status to active
     * @param activityRepo dependency injection, activity repo
     * @param activityId an IdTransmission object with the activity id
     */
    public static void reactivateActivity(ActivityRepo activityRepo, StrictIndex activityId) {
        Activity activity = getActivity(activityRepo, activityId);

        // Check if already canceled?
        if (activity.getStatus() == ActivityStatus.ACTIVE) {
            throw new GiddException(Status.ACTIVITY_ALREADY_ACTIVE, "Activity is already active");
        }

        activity.setStatus(ActivityStatus.ACTIVE);
        activityRepo.save(activity);
    }

    public static void alterActivity(ActivityRepo activityRepo, Activity activity){

    }


    /**
     * Update specified activity according to model.
     * @param activityRepo The repo to interact with.
     * @param id The id of activity.
     * @param newActivityData The new activity data.
     * @throws GiddException if activity could not be found.
     */


    public static void updateActivity(ActivityRepo activityRepo, EquipmentRepo equipmentRepo, StrictIndex id, UpdateActivityModel newActivityData) {

        Activity currentActivity = activityRepo.findById(id.getRequestId()).orElse(null);

        if (currentActivity == null) {
            throw new GiddException(Status.ACTIVITY_NOT_FOUND, "Aktiviteten finnes ikke");
        }

//        List<Equipment> existingEquipment = equipmentRepo.getEquipmentListByActivityId(id.getRequestId());
//        newActivityData.getEquipment().stream().map(e -> new Equipment(e.getId(), e.getName(), e.));

        currentActivity.setName(newActivityData.getName());
        currentActivity.setDescription(newActivityData.getDescription());
        currentActivity.setLocation(newActivityData.getLocation());
        currentActivity.setStartTime(newActivityData.getStartTime());
        currentActivity.setEndTime(newActivityData.getEndTime());
        currentActivity.setCapacity(newActivityData.getCapacity());
        currentActivity.setInterests(newActivityData.getInterests());
        currentActivity.setActivityLevel(newActivityData.getActivityLevel());

        currentActivity.inspect();

        List<Equipment> newEquipment = new ArrayList<>();
        if (newActivityData.getEquipment().size() > 0) {
            newEquipment = equipmentRepo.saveAll(
                    newActivityData.getEquipment()
                            .stream()
                            .map(e -> new Equipment(e.getId(), e.getName(), new Activity(id.getRequestId())))
                            .collect(Collectors.toList())
            );
        }

        currentActivity.setEquipment(newEquipment);

        activityRepo.save(currentActivity);
    }

    /**
     * Retreived specified activity.
     * @param activityRepo The repo to interact with.
     * @param id id of activity.
     * @return The activity.
     * @throws GiddException if activity could not be found.
     */

    public static Activity getActivity(ActivityRepo activityRepo, StrictIndex id){
        // Perform all neccessary checks if applicable
        Optional<Activity> activity = activityRepo.findById(id.getRequestId());
        if (activity.isEmpty()){
            throw new GiddException(Status.ACTIVITY_NOT_FOUND, "Cannot find activity with ID: " + id.getRequestId());
        }

        // Everything is a-ok
        return activity.get();
    }


    public static GetActivityResponseModel getSingleActivity(ActivityRepo activityRepo, StrictIndex id){
        // Perform all neccessary checks if applicable
        Optional<Activity> activity = activityRepo.findById(id.getRequestId());
        if (activity.isEmpty()){
            throw new GiddException(Status.ACTIVITY_NOT_FOUND, "Cannot find activity with ID: " + id.getRequestId());
        }

        Activity stub = activity.get();
        GetActivityResponseModel getActivityResponseModel
                = new GetActivityResponseModel(
                stub.getId(), stub.getName(), stub.getDescription(),
                new GetActivityResponseModel.User(
                        stub.getCreator().getId(), stub.getCreator().getPersona().getFirstName(),
                        stub.getCreator().getPersona().getSurName()
                ), stub.getLocation(), stub.getCapacity(),
                stub.getEquipment()
                        .stream()
                        .map(e ->
                                new GetActivityResponseModel.Equipment(
                                        e.getId(), e.getName(), e.getActivity().getId(), e.getClaimedByUser() == null ? null : e.getClaimedByUser().getId()))
                        .collect(Collectors.toList()),
                stub.getEnrolledUsers()
                        .stream()
                        .map(u -> new GetActivityResponseModel.User(
                                u.getId(), u.getPersona().getFirstName(), u.getPersona().getSurName()))
                        .collect(Collectors.toList()), stub.getActivityLevel(), stub.getInterests(),
                stub.getStartTime(), stub.getEndTime()
        );
        // Everything is a-ok
        return getActivityResponseModel;
    }

    /**
     * Gets all activities.
     * @param activityRepo The repo to interact with.
     * @return All activities.
     */

    public static List<Activity> getActivities(ActivityRepo activityRepo){
        // Perform all neccessary checks if applicable
        List<Activity> activity = activityRepo.findAll();
        if (activity.isEmpty()){
            return activity;
        }

        // Everything is a-ok
        return activity;
    }

    /**
     * Adds the user to the enrolledUsers list for the activity
     *
     * @param activityRepo dependency injection, activity repo
     * @param activityId an IDTransmission object with the activity id
     * @param userRepo dependency injection, user repo
     * @param userMail
     */
    public static String joinActivity(ActivityRepo activityRepo, StrictIndex activityId, WaitlistRepo waitlistRepo, User user) {

        if (Optional.ofNullable(user).isEmpty()) {
            throw new GiddException(Status.USER_NOT_FOUND, "User not found");
        }


        // Get activity and add user to enrolledUsers
        Activity activity = ActivityInteractor.getActivity(activityRepo, activityId);

        // Check if there is space
        if (activity.getEnrolledUsers().size() >= activity.getCapacity()) {
            activity.addUserToWaitList(user);
            // Save
            waitlistRepo.save(activity.getWaitListUsers().get(activity.getWaitListUsers().size()-1));
            activityRepo.save(activity);
            return "Aktiviteten var dessverre full. Du er nå meldt på ventelisten med posisjon " + activity.getWaitListUsers().size();
        }
        else {
            // Add user to enrolledUsers list
            activity.getEnrolledUsers().add(user);
            user.getEnrolledActivities().add(activity);

            // Save
            activityRepo.save(activity);

            return "Du er nå påmeldt på " + activity.getName();
        }
    }

    /**
     * Removes the user from the enrolledUsers list of the activity
     * @param activityRepo dependency injection, activity repo
     * @param activityId an IDTransmission object with the activity id
     * @param user a User object
     */

    public static String leaveActivity(ActivityRepo activityRepo,
                                       StrictIndex activityId,
                                       UserService userService,
                                       WaitlistRepo waitlistRepo,
                                       JavaMailSender javaMailSender) {
        User thisUser = userService.getThisUser();
        String msg = "";

        // Get activity and remove user from enrolledUsers
        Activity activity = ActivityInteractor.getActivity(activityRepo, new StrictIndex(activityId.getRequestId()));
        int indexOfWLO =  activity.getWaitListUsers().stream().map(ActivityWaitList::getUser).collect(Collectors.toList()).indexOf(thisUser);


        //checks if user is enrolled in an activity
        if (activity.getEnrolledUsers().stream().anyMatch(u -> u.getId().equals(thisUser.getId()))) {
            msg = " " + activity.getName() +  "!";
            // Remove user from enrolledUsers list
            activity.getEnrolledUsers().remove(thisUser);
            thisUser.getEnrolledActivities().remove(activity);
            //update waitlist
            if(activity.getWaitListUsers().size()>0){
                User userToBeAddedToEnrolled = activity.getWaitListUsers().get(0).getUser();
                ActivityWaitList waitListObjectToBeRemoved = activity.getWaitListUsers().get(0);
                activity.getEnrolledUsers().add(userToBeAddedToEnrolled);
                activity.removeUserFromWaitList(userToBeAddedToEnrolled);
                userService.alterUser(userToBeAddedToEnrolled);
                waitlistRepo.save(waitListObjectToBeRemoved);

                javaMailSender.send(mime -> {
                    mime.setRecipient(Message.RecipientType.TO, new InternetAddress(userToBeAddedToEnrolled.getUsername()));
                    mime.setSubject("Du har fått plass i " + activity.getName() + "!");
                    mime.setText("Vi håper du koser deg masse. Gidd videre!");
                });
            }
        }
        else if(indexOfWLO != -1){
            msg = " ventelista på " + activity.getName() + "!";
            waitlistRepo.save(activity.getWaitListUsers().get(indexOfWLO));
            activity.removeUserFromWaitList(thisUser);
        }
        else{
            // Throw exception if user is not enrolled
            throw new GiddException(Status.ACTIVITY_NOT_ENROLLED, "User is not enrolled");
        }

        // Save
        activityRepo.save(activity);
        userService.alterUser(thisUser);
        return "Du har nå blitt meldt av" + msg;
    }

    /**
     * Grants user points for participating in activities.
     * @param activityRepo Repo to interact with.
     * @param activityId Id of activity.
     * @param userService Userservice to interact with in order to save points.
     * @param user The current user.
     * @param location The current location.
     * @throws GiddException if user is not enrolled.
     * @throws GiddException if activity has not started.
     * @throws GiddException if location is invalid.
     * @throws GiddException if user has already received points.
     * @throws GiddException if user is too far away from activity.
     */

    public static void getPointsFromActivity(ActivityRepo activityRepo, StrictIndex activityId, UserService userService, User user, Location location) {

        // Get activity
        Activity activity = ActivityInteractor.getActivity(activityRepo, new StrictIndex(activityId.getRequestId()));

        // Throw exception if user is not enrolled
        if (activity.getEnrolledUsers()
                .stream()
                .noneMatch(u -> u.getId().equals(user.getId()))) {
            throw new GiddException(Status.ACTIVITY_NOT_ENROLLED, "User is not enrolled");
        }

        // Check if activity has started
        if (LocalDateTime.now().isBefore(activity.getStartTime())) {
            throw new GiddException(Status.ACTIVITY_NOT_STARTED, "Activity has not started yet");
        }

        // Check if there is
        if (Optional.ofNullable(location).isEmpty()) {
            throw new GiddException(Status.ACTIVITY_INVALID_LOCATION, "Invalid user location data");
        }

        // Check if user already has claimed points/checked in
        if (activity.getCheckedInUsers().stream().anyMatch(u -> u.getId().equals(user.getId()))) {
            throw new GiddException(Status.ACTIVITY_ALREADY_CHECKED_IN, "Already checked in");
        }

        // Check user's distance from the activity
        if (activity.getLocation().distance(location) > 200) {
            throw new GiddException(Status.ACTIVITY_TOO_FAR_AWAY, "Too far away to claim points");
        }

        activity.getCheckedInUsers().add(user);

        user.addPoints(50);
        userService.alterUser(user);
        activityRepo.save(activity);
    }

    /**
     * Retreives the provided users organized activities.
     * @param activityRepo The repo to interact with.
     * @param user User with activities.
     * @return The organized activities.
     */

    public static List<Activity> getOrganizedActivities(ActivityRepo activityRepo,User user) {
       return activityRepo.getOrganizedActivities(user);
    }

    /**
     * Retreives the provided users enrolled activities.
     * @param activityRepo The repo to interact with.
     * @param user User with activities.
     * @return The enrolled activities.
     */

    public static List<Activity> getEnrolledActivities(ActivityRepo activityRepo,User user) {
        return activityRepo.getEnrolledActivities(user);
    }

    /**
     * Retreives activities according to some criteria.
     * @param activityCriteriaRepo A criteria-repo to interact with.
     * @param activityFilter The filter containing criteria.
     * @return The matched activities with paging meta-information.
     */

    public static Page<Activity> filterActivities(ActivityCriteriaRepo activityCriteriaRepo,
                                                  ActivityFilter activityFilter){
        return activityCriteriaRepo
                .findAllWithFilters(activityFilter.getActivityPage(), activityFilter.getActivitySearchCriteria());
    }

    /**
     * Gets distinct locations with activities.
     * @param activityRepo Repo to interact with.
     * @return distinct location names.
     */

    public static List<String> getUniqueLocations(ActivityRepo activityRepo){
        return activityRepo.getDistinctCities();
    }

    /**
     * Gets distinct interests in activities.
     * @param activityRepo The repo to interact with.
     * @return distinct interests.
     */

    public static List<String> getUniqueInterests(ActivityRepo activityRepo){
        return activityRepo.getDistinctInterests();
    }
}