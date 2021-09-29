package v5.gidd.service;

import org.springframework.beans.factory.annotation.Autowired;
import v5.gidd.entities.activity.Activity;
import v5.gidd.entities.equipment.Equipment;
import v5.gidd.entities.message.Message;
import v5.gidd.entities.message.StrictIndex;
import v5.gidd.repo.ActivityRepo;
import v5.gidd.repo.EquipmentRepo;
import v5.gidd.usecase.ActivityInteractor;
import v5.gidd.usecase.EquipmentInteractor;

import java.util.List;

@org.springframework.stereotype.Service
public class EquipmentService extends Service<EquipmentRepo> {

    @Autowired
    private EquipmentRepo equipmentRepo;
    @Autowired
    private UserService userService;

    /**
     * Creates new equipment.
     * @param equipment The equipment model.
     * @return Request status from server.
     */

    @Autowired
    private ActivityRepo activityRepo;

    public Message<Void> createEquipment(Equipment equipment){
        return execute(equipment, EquipmentInteractor::registerEquipment);
    }

    /**
     * Alters existing equipment.
     * @param equipment The target equipment model.
     * @return Request status from server.
     */

    public Message<Void> alterEquipment(Equipment equipment){
        return execute(equipment, EquipmentInteractor::alterEquipment);
    }

    /**
     * Deletes existing equipment.
     * @param equipment The target entity.
     * @return Request status from server.
     */

    public Message<Void> deleteEquipment(Equipment equipment){
        return execute(equipment, EquipmentInteractor::deleteEquipment);
    }

    /**
     * Claims equipment for this user.
     * @param equipmentId Id of the equipment.
     * @return Request status from server.
     */

    public Message<Void> claimEquipment(StrictIndex equipmentId) {
        Equipment equipment = EquipmentInteractor.getEquipmentById(equipmentRepo, equipmentId.getRequestId());
        return execute(equipmentId, e -> EquipmentInteractor.claimEquipment(equipmentRepo, equipment, userService.getThisUser()));
    }

    /**
     * Unclaims equipment for this user.
     * @param equipmentId Id of the equipment.
     * @return Request status from server.
     */

    public Message<Void> unclaimEquipment(StrictIndex equipmentId) {
        Equipment equipment = EquipmentInteractor.getEquipmentById(equipmentRepo, equipmentId.getRequestId());
        return execute(equipmentId, e -> EquipmentInteractor.unclaimEquipment(equipmentRepo, equipment, userService.getThisUser()));
    }

    public Message<List<Equipment>> getEquipmentListByActivity(StrictIndex activityId) {
        Activity activity = ActivityInteractor.getActivity(activityRepo, activityId);
        return query(activity, EquipmentInteractor::getEquipmentListByActivity);
    }

    @Override
    protected EquipmentRepo getRepo() {
        return equipmentRepo;
    }
}
