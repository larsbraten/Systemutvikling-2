package v5.gidd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import v5.gidd.entities.equipment.Equipment;
import v5.gidd.entities.message.Message;
import v5.gidd.entities.message.StrictIndex;
import v5.gidd.service.EquipmentService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Contains endpoints responsible for equipment interactions. Controller, i.e. the superclass,
 * is responsible for formatting http-responses with whatever status-code the service yielded in the payload.
 * Please refer to service doc for more in depth detail.
 */

@RestController
public class EquipmentController extends Controller<EquipmentService>{

    @Autowired
    private EquipmentService equipmentService;

    /**
     * Creates new equipment.
     * @param equipment The equipment to persist.
     * @return Request response from server.
     */

    @PostMapping("/equipment")
    public ResponseEntity<Message<Void>> createEquipment(@RequestBody Equipment equipment) {
        return mapRequest(equipment, equipmentService::createEquipment);
    }

    /**
     * Alters some existing equipment.
     * @param equipment The equipment to alter.
     * @return Request response from server.
     */

    @PutMapping(value = "/equipment", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Message<Void>> alterEquipment(@RequestBody Equipment equipment) {
        return mapRequest(equipment, equipmentService::alterEquipment);
    }

    /**
     * Deletes some existent equipment.
     * @param equipment The equipment to delete.
     * @return Request response from server.
     */

    @DeleteMapping(value = "/equipment", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Message<Void>> deleteEquipment(@RequestBody Equipment equipment) {
        return mapRequest(equipment, equipmentService::deleteEquipment);
    }

    /**
     * Lets a user signal that they will bring the equipment to the activity
     * @param equipmentId equipment Id
     * @return Request response from server.
     */

    @PutMapping(value = "/equipment/{equipmentId}/claim", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Message<Void>> claimEquipment(@PathVariable long equipmentId) {
        return mapRequest(new StrictIndex(equipmentId), equipmentService::claimEquipment);
    }

    /**
     * Lets a user remove their signal that they will bring the equipment to the activity
     * @param equipmentId equipment Id
     * @return Request response from server.
     */

    @DeleteMapping(value = "/equipment/{equipmentId}/claim", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Message<Void>> unclaimEquipment(@PathVariable long equipmentId) {
        return mapRequest(new StrictIndex(equipmentId), equipmentService::unclaimEquipment);
    }

    @Override
    protected EquipmentService getService() {
        return equipmentService;
    }
}
