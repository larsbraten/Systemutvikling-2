package v5.gidd.entities.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import v5.gidd.GiddApplication;
import v5.gidd.entities.activity.Activity;
import v5.gidd.entities.activity.attributes.ActivityLevel;
import v5.gidd.entities.activity.waitList.ActivityWaitList;
import v5.gidd.entities.equipment.Equipment;
import v5.gidd.entities.message.Status;
import v5.gidd.entities.user.attributes.ContactInfo;
import v5.gidd.entities.user.attributes.Credentials;
import v5.gidd.entities.user.attributes.Persona;
import v5.gidd.entities.user.attributes.Quality;
import v5.gidd.throwable.GiddException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = MOCK, classes = GiddApplication.class)
class UserTest {

    @Test
    void TestInvalidPasswordTooShort() {
        User user = new User(new Persona("Ole", "Nilsen", LocalDate.now()),
                new ContactInfo("email1@e.com", "12345672"), new Credentials("123@test.no", "123"), Quality.VERY_LOW, 50);
        GiddException e = assertThrows(GiddException.class, user::inspect);

        String expectedMessage = "Password must be atleast 7 characters";
        Status expectedStatus = Status.INVALID_PASSWORD;

        String actualMessage = e.getDescription();
        Status status = e.getStatus();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedStatus, status);
    }

    @Test
    void TestInvalidPasswordEqualsToEmail() {
        User user = new User(new Persona("Ole", "Nilsen", LocalDate.now()),
                new ContactInfo("123@test.no", "12345672"), new Credentials("123@test.no", "123@test.no"), Quality.VERY_LOW, 200);
        GiddException e = assertThrows(GiddException.class, user::inspect);

        String expectedMessage = "Password cannot be same as email";
        Status expectedStatus = Status.INVALID_PASSWORD;

        String actualMessage = e.getDescription();
        Status status = e.getStatus();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedStatus, status);
    }

    @Test
    void TestInvalidFirstnameLowerCase() {
        User user = new User(new Persona("le", "Nilsen", LocalDate.now()),
                new ContactInfo("123@test.no", "12345672"),
                new Credentials("123@test.no", "12345678"), Quality.VERY_LOW, 500);
        GiddException e = assertThrows(GiddException.class, user::inspect);

        String expectedMessage = "First name must begin in upper, succeeded by lower case letters.";
        Status expectedStatus = Status.INVALID_FIRST_NAME;

        String actualMessage = e.getDescription();
        Status status = e.getStatus();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedStatus, status);
    }

    @Test
    void TestInvalidLastnameLowerCase() {
        User user = new User(new Persona("Ole", "nilsen", LocalDate.now()),
                new ContactInfo("123@test.no", "12345672"),
                new Credentials("123@test.no", "12345678"), Quality.VERY_LOW, 400);
        GiddException e = assertThrows(GiddException.class, user::inspect);

        String expectedMessage = "Last name must begin in upper, succeeded by lower case letters.";
        Status expectedStatus = Status.INVALID_LAST_NAME;

        String actualMessage = e.getDescription();
        Status status = e.getStatus();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedStatus, status);
    }

    @Test
    void TestInvalidLastnameEmpty() {
        User user = new User(new Persona("Ole", "", LocalDate.now()),
                new ContactInfo("123@test.no", "12345672"),
                new Credentials("123@test.no", "12345678"), Quality.VERY_LOW, 450);
        GiddException e = assertThrows(GiddException.class, user::inspect);

        String expectedMessage = "Last name cannot be empty.";
        Status expectedStatus = Status.INVALID_LAST_NAME;

        String actualMessage = e.getDescription();
        Status status = e.getStatus();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedStatus, status);
    }

    @Test
    void TestInvalidFirstnameEmpty() {
        User user = new User(new Persona("", "Nilsen", LocalDate.now()),
                new ContactInfo("123@test.no", "12345672"),
                new Credentials("123@test.no", "12345678"), Quality.VERY_LOW, 1);
        GiddException e = assertThrows(GiddException.class, user::inspect);

        String expectedMessage = "First name cannot be empty.";
        Status expectedStatus = Status.INVALID_FIRST_NAME;

        String actualMessage = e.getDescription();
        Status status = e.getStatus();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedStatus, status);
    }

    @Test
    void TestInvalidPasswordEqualsFirstName() {
        User user = new User(new Persona("Mohammed", "Nilsen", LocalDate.now()),
                new ContactInfo("123@test.no", "12345672"),
                new Credentials("123@test.no", "Mohammed"), Quality.VERY_LOW, 0);
        GiddException e = assertThrows(GiddException.class, user::inspect);

        String expectedMessage = "Password cannot be same as name";
        Status expectedStatus = Status.INVALID_PASSWORD;

        String actualMessage = e.getDescription();
        Status status = e.getStatus();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedStatus, status);
    }

    @Test
    void TestInvalidPasswordEqualsLastName() {
        User user = new User(new Persona("Nilsen", "Mohammed", LocalDate.now()),
                new ContactInfo("123@test.no", "12345672"),
                new Credentials("123@test.no", "Mohammed"), Quality.VERY_LOW, 55);
        GiddException e = assertThrows(GiddException.class, user::inspect);

        String expectedMessage = "Password cannot be same as name";
        Status expectedStatus = Status.INVALID_PASSWORD;

        String actualMessage = e.getDescription();
        Status status = e.getStatus();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedStatus, status);
    }

    @Test
    void TestInvalidPasswordEqualsFullName() {
        User user = new User(new Persona("Nilsen", "Mohammed", LocalDate.now()),
                new ContactInfo("123@test.no", "12345672"),
                new Credentials("123@test.no", "NilsenMohammed"), Quality.VERY_LOW, 444);
        GiddException e = assertThrows(GiddException.class, user::inspect);

        String expectedMessage = "Password cannot be same as name";
        Status expectedStatus = Status.INVALID_PASSWORD;

        String actualMessage = e.getDescription();
        Status status = e.getStatus();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedStatus, status);
    }

    @Test
    void testUserObjectManipulationGettersSetters() {
        Persona persona = new Persona();
        persona.setBirthday(LocalDate.of(2020, 1, 1));
        persona.setFirstName("Test");
        persona.setSurName("Testesen");

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setEmail("test@testesen.no");
        contactInfo.setPhoneNumber("75757575");

        Credentials credentials = new Credentials();
        credentials.setEmail("test@testesen.no");
        credentials.setPassword("åepwojema2pA9185!");

        List<ActivityWaitList> waitListedActivities = new ArrayList<>();
        List<Activity> createdActivities = new ArrayList<>();
        List<Activity> enrolledActivities = new ArrayList<>();
        List<Equipment> claimedEquipment = new ArrayList<>();

        User user = new User();
        user.setPersona(persona);
        user.setContactInfo(contactInfo);
        user.setActivityLevel(Quality.MEDIUM);
        user.setVerified(true);
        user.setWaitListedActivities(waitListedActivities);
        user.setCreatedActivities(createdActivities);
        user.setClaimedEquipment(claimedEquipment);
        user.setPoints(1000);
        user.setCredentials(credentials);

        // Manipulations
        assertEquals(1000, user.getPoints());
        user.removePoints(500);
        assertEquals(500, user.getPoints());
        user.addPoints(1000);
        assertEquals(1500, user.getPoints());

        // Other assertions
        assertEquals(0, user.getClaimedEquipment().size());
        assertEquals(0, user.getCreatedActivities().size());
        assertEquals("åepwojema2pA9185!", user.getPassword());
        assertEquals(0, user.getWaitListedActivities().size());
    }
}