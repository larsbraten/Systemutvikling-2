package v5.gidd.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import v5.gidd.entities.message.Message;
import v5.gidd.entities.message.StrictIndex;
import v5.gidd.entities.user.attributes.ContactInfo;
import v5.gidd.entities.user.attributes.Credentials;
import v5.gidd.entities.user.attributes.Persona;
import v5.gidd.entities.user.attributes.Quality;
import v5.gidd.repo.UserRepo;
import v5.gidd.usecase.EquipmentInteractor;
import v5.gidd.entities.activity.Activity;
import v5.gidd.entities.equipment.Equipment;
import v5.gidd.entities.message.Status;
import v5.gidd.entities.user.User;
import v5.gidd.repo.EquipmentRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class EquipmentServiceTest {

    @InjectMocks
    EquipmentService equipmentService;

    @Mock
    EquipmentRepo equipmentRepo;

    @Mock
    UserService userService;

    @Mock
    UserRepo userRepo;

    @Mock
    EquipmentInteractor equipmentInteractor;

    private Equipment equipment;
    private User user;
    private Activity activity;

    @BeforeEach
    void setup() {
        user = new User(new Persona("Ole", "Nilsen", LocalDate.now()),
                new ContactInfo("email1@e.com", "12345672"), new Credentials("test@test.no", "12345678"), Quality.VERY_LOW, 0);
        user.setId(1L);
        Optional<User> optional = Optional.of(user);

        List<Activity> enrolledActivities = new ArrayList<>();
        activity = new Activity();
        activity.setId(1L);
        enrolledActivities.add(activity);

        equipment = new Equipment("Ball");
        equipment.setId(2L);
        equipment.setActivity(activity);

        user.setEnrolledActivities(enrolledActivities);

        when(equipmentRepo.save(equipment)).thenReturn(equipment);

        when(equipmentRepo.findById(2L)).thenReturn(Optional.ofNullable(equipment));
    }

    @WithMockUser
    @Test
    void testClaimEquipmentSuccess() {
        StrictIndex strictIndex = new StrictIndex(2L);
        lenient().when(userService.getThisUser()).thenReturn(user);

        Message<Void> message = equipmentService.claimEquipment(strictIndex);
        verify(equipmentRepo, times(1)).findById(2L);

        assertEquals(Status.Ok, message.getStatus());
    }

    @WithMockUser
    @Test
    void testUnclaimEquipmentSuccess() {
        equipment.setClaimedByUser(user);

        StrictIndex idTransmission = new StrictIndex(2L);
        lenient().when(userService.getThisUser()).thenReturn(user);

        Message<Void> message = equipmentService.unclaimEquipment(idTransmission);
        verify(equipmentRepo, times(1)).findById(2L);

        assertEquals(Status.Ok, message.getStatus());
    }
}