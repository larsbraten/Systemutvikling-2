package v5.gidd.usecase;

import v5.gidd.entities.activity.Activity;
import v5.gidd.entities.equipment.Equipment;

import v5.gidd.entities.message.Message;
import v5.gidd.entities.message.Status;
import v5.gidd.entities.user.User;
import v5.gidd.repo.EquipmentRepo;
import v5.gidd.throwable.GiddException;

import java.util.List;
import java.util.Optional;

public class EquipmentInteractor {

    /**
     * Not implemented.
     * @param equipmentRepo
     * @param equipment
     */

    public static void registerEquipment(EquipmentRepo equipmentRepo, Equipment equipment){
        throw new GiddException(Status.NOT_IMPLEMENTED, "Not implemented yet.");
    }

    /**
     * Not implemented.
     * @param equipmentRepo
     * @param equipment
     */

    public static void deleteEquipment(EquipmentRepo equipmentRepo, Equipment equipment){
        throw new GiddException(Status.NOT_IMPLEMENTED, "Not implemented yet.");
    }

    /**
     * Not implemented.
     * @param equipmentRepo
     * @param equipment
     */

    public static void alterEquipment(EquipmentRepo equipmentRepo, Equipment equipment){
        throw new GiddException(Status.NOT_IMPLEMENTED, "Not implemented yet.");
    }

    /**
     * Retreives equipment by id.
     * @param equipmentRepo Repo to interact with.
     * @param equipmentId Id of equipment.
     * @return The equipment.
     * @throws GiddException if equipment were not found.
     */

    public static Equipment getEquipmentById(EquipmentRepo equipmentRepo, long equipmentId) {
        return equipmentRepo.findById(equipmentId)
                .orElseThrow(() -> new GiddException(Status.EQUIPMENT_NOT_FOUND, "Equipment not found"));
    }

    /**
     * Claims equipment for user, signalling that they take responsibility to bring it to an activity.
     * @param equipmentRepo Repo to interact with.
     * @param equipment Equipment to claim.
     * @param user User to claim equipment.
     * @throws GiddException if user not found.
     * @throws GiddException if equipment not found.
     * @throws GiddException if user not enrolled to activity.
     * @throws GiddException if equipment already claimed.
     */

    public static void claimEquipment(EquipmentRepo equipmentRepo, Equipment equipment, User user) {
        // Check if user is null
        if (Optional.ofNullable(user).isEmpty()) {
            throw new GiddException(Status.USER_NOT_FOUND, "User not found");
        }

        // Check if equipment exists
        if (Optional.ofNullable(equipment).isEmpty()) {
            throw new GiddException(Status.EQUIPMENT_NOT_FOUND, "Equipment not found");
        }

        // Check if user is enrolled to activity
        /*if (user.getEnrolledActivities().stream().noneMatch(a -> a.getId().equals(equipment.getActivity().getId()))||user.getCreatedActivities().stream().noneMatch(a -> a.getId().equals(equipment.getActivity().getId()))) {
            throw new GiddException(Status.EQUIPMENT_NOT_ENROLLED, "User is not enrolled to activity");
        }*/

        // Is equipment already claimed?
        if (Optional.ofNullable(equipment.getClaimedByUser()).isPresent()) {
            throw new GiddException(Status.EQUIPMENT_ALREADY_CLAIMED, "Equipment already claimed");
        }

        // All checks passed, set User as claimedByUser and save to repo
        equipment.setClaimedByUser(user);
        equipmentRepo.save(equipment);
    }

    /**
     * Unclaims equipment from user.
     * @param equipmentRepo Repo to interact with.
     * @param equipment Equipment to unclaim.
     * @param user User to unclaim from.
     * @throws GiddException if user not found
     * @throws GiddException if equipment not found.
     * @throws GiddException if user not enrolled to activity.
     * @throws GiddException if equipment is not claimed.
     * @throws GiddException if user has not claimed equipment.
     */

    public static void unclaimEquipment(EquipmentRepo equipmentRepo, Equipment equipment, User user) {
        // Check if user is null
        if (Optional.ofNullable(user).isEmpty()) {
            throw new GiddException(Status.USER_NOT_FOUND, "User not found");
        }

        // Check if equipment exists
        if (Optional.ofNullable(equipment).isEmpty()) {
            throw new GiddException(Status.EQUIPMENT_NOT_FOUND, "Equipment not found");
        }

        // Check if user is enrolled to activity
        if (user.getEnrolledActivities().stream().noneMatch(a -> a.getId().equals(equipment.getActivity().getId()))) {
            throw new GiddException(Status.EQUIPMENT_NOT_ENROLLED, "User is not enrolled to activity");
        }

        // Is equipment not claimed?
        if (Optional.ofNullable(equipment.getClaimedByUser()).isEmpty()) {
            throw new GiddException(Status.EQUIPMENT_NOT_CLAIMED, "Equipment is not already claimed");
        }

        // Is it the user that claimed equipment that is unclaiming?
        if (!equipment.getClaimedByUser().getId().equals(user.getId())) {
            throw new GiddException(Status.EQUIPMENT_UNCLAIMABLE, "Equipment claim does not belong to this user");
        }

        // All checks passed, set claimedByUser to null and save to repo
        equipment.setClaimedByUser(null);
        equipmentRepo.save(equipment);
    }

    public static List<Equipment> getEquipmentListByActivity(EquipmentRepo equipmentRepo, Activity activity) {
        return equipmentRepo.getEquipmentListByActivityId(activity.getId());
    }
}
