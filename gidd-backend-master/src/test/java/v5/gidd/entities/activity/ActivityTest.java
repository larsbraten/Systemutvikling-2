package v5.gidd.entities.activity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import v5.gidd.entities.activity.attributes.ActivityLevel;
import v5.gidd.entities.activity.attributes.Location;
import v5.gidd.entities.message.Status;
import v5.gidd.throwable.GiddException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest {
    private static Activity testActivity;

    /**
     * Sets up a dummy Activity object with valid values
     */
    @BeforeAll
    static void setup() {
        testActivity = new Activity();
        testActivity.setName("Fotball");
        testActivity.setCapacity(10);
        testActivity.setActivityLevel(ActivityLevel.LOW);
        testActivity.setLocation(new Location("Ilaparken"));

        List<String > interestsList = new ArrayList<>();
        interestsList.add("fotball");
        interestsList.add("løping");

        testActivity.setInterests(interestsList);

        LocalDateTime startTime = LocalDate.of(2024, 4, 14).atTime(17, 0, 0);
        LocalDateTime endTime = LocalDate.of(2024, 4, 14).atTime(19, 0, 0);
    }

    /**
     * Resets dummy Activity object to valid values
     */
    static void reset() {
        testActivity.setName("Fotball");
        testActivity.setCapacity(10);
        testActivity.setActivityLevel(ActivityLevel.LOW);
        testActivity.setLocation(new Location("Ilaparken"));
    }

    /**
     * Tests if an Exception is thrown when the name of the activity is empty
     */
    @Test
    void testExceptionOnEmptyName() {
        reset();
        testActivity.setName("");

        GiddException thrown = assertThrows(GiddException.class, testActivity::inspect, "Expected inspect() to throw exception");
        assertEquals(Status.ACTIVITY_INVALID_NAME, thrown.getStatus());
    }

    @Test
    /**
     * Tests if an exception is thrown if a Location object is not present or null
     */
    void testExceptionOnEmptyLocation() {
        reset();
        testActivity.setLocation(null);

        GiddException thrown = assertThrows(GiddException.class, testActivity::inspect, "Expected inspect() to throw exception");
        assertEquals(Status.ACTIVITY_INVALID_LOCATION, thrown.getStatus());
    }

    /**
     * Tests if an exception is thrown when the Activity capacity value is negative
     */
    @Test
    void testExceptionOnNegativeCapacity() {
        reset();
        testActivity.setCapacity(-3);

        GiddException thrown = assertThrows(GiddException.class, testActivity::inspect, "Expected inspect() to throw exception");
        assertEquals(Status.ACTIVITY_INVALID_CAPACITY, thrown.getStatus());
    }
}
