package v5.gidd.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import v5.gidd.GiddApplication;
import v5.gidd.entities.activity.Activity;
import v5.gidd.entities.equipment.Equipment;
import v5.gidd.entities.message.Status;
import v5.gidd.entities.user.User;
import v5.gidd.repo.EquipmentRepo;
import v5.gidd.service.UserService;
import v5.gidd.throwable.GiddException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = MOCK, classes = GiddApplication.class)
class EquipmentInteractorTest {
    @InjectMocks
    EquipmentInteractor equipmentInteractor;
    @MockBean
    UserService userService;

    @MockBean
    EquipmentRepo equipmentRepo;

    private User user;

    private Activity activity;

    private Equipment equipment;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(1L);

        List<Activity> enrolledActivities = new ArrayList<>();
        activity = new Activity();
        activity.setId(1L);
        enrolledActivities.add(activity);

        equipment = new Equipment("Ball");
        equipment.setActivity(activity);

        user.setEnrolledActivities(enrolledActivities);

        when(equipmentRepo.save(equipment)).thenReturn(equipment);
    }

    @Test
    void testClaimEquipmentAndVerifyExecutions() {
        equipmentInteractor.claimEquipment(equipmentRepo, equipment, user);

        // Verify number of invocations
        verify(equipmentRepo, times(1)).save(equipment);
    }



    @Test
    void testClaimEquipmentFailWhenAlreadyClaimed() {
        User otherUser = User.fromId(2L);

        // Enroll otherUser to the same activity
        List<Activity> enrolledActivities = new ArrayList<>();
        activity = new Activity();
        activity.setId(1L);
        enrolledActivities.add(activity);
        otherUser.setEnrolledActivities(enrolledActivities);

        // Set otherUser to have claimed the equipment
        equipment.setClaimedByUser(otherUser);

        GiddException thrown = assertThrows(GiddException.class, () -> equipmentInteractor.claimEquipment(equipmentRepo, equipment, user), "Expected equipmentInteractor.unclaimEquipment() to throw exception");
        assertEquals(Status.EQUIPMENT_ALREADY_CLAIMED, thrown.getStatus());
    }

    @Test
    void testUnclaimEquipmentAndVerifyExecutions() {
        equipment.setClaimedByUser(user);

        equipmentInteractor.unclaimEquipment(equipmentRepo, equipment, user);

        // Verify number of invocations
        verify(equipmentRepo, times(1)).save(equipment);
    }

    @Test
    void testUnclaimEquipmentFailOnNotClaimedEquipment() {
        GiddException thrown = assertThrows(GiddException.class, () -> EquipmentInteractor.unclaimEquipment(equipmentRepo, equipment, user), "Expected equipmentInteractor.unclaimEquipment() to throw exception");
        assertEquals(Status.EQUIPMENT_NOT_CLAIMED, thrown.getStatus());
    }

    @Test
    void testUnclaimEquipmentWithNotSameOwnership() {
        User otherUser = User.fromId(2L);

        // Enroll otherUser to the same activity
        List<Activity> enrolledActivities = new ArrayList<>();
        activity = new Activity();
        activity.setId(1L);
        enrolledActivities.add(activity);
        otherUser.setEnrolledActivities(enrolledActivities);

        // Set otherUser to have claimed the equipment
        equipment.setClaimedByUser(otherUser);

        GiddException thrown = assertThrows(GiddException.class, () -> EquipmentInteractor.unclaimEquipment(equipmentRepo, equipment, user), "Expected equipmentInteractor.unclaimEquipment() to throw exception");
        assertEquals(Status.EQUIPMENT_UNCLAIMABLE, thrown.getStatus());
    }
}

