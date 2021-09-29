package v5.gidd.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import v5.gidd.entities.activity.Activity;
import v5.gidd.entities.user.User;

import java.util.List;

@Repository
public interface ActivityRepo extends JpaRepository<Activity, Long> {

    /*@Query(
            value = "SELECT a FROM Activity a WHERE a.creator = ?1"
    )
    List<Activity> getOrganizedActivities(User u);

    @Query(
            value = "SELECT * FROM Activity WHERE id IN (SELECT activity_id FROM activity_user WHERE user_id = ?1)",
            nativeQuery = true
    )
    List<Activity> getEnrolledActivities(User u);*/

    @Query(
            value = "SELECT * FROM activity WHERE creator_id = ?1 AND STATUS = 'ACTIVE' AND END_TIME >= CURRENT_TIMESTAMP",
            nativeQuery = true
    )
    List<Activity> getOrganizedActivities(User u);

    @Query(
            value = "SELECT * FROM activity WHERE STATUS = 'ACTIVE' AND END_TIME >= CURRENT_TIMESTAMP AND id IN (SELECT activity_id FROM activity_user WHERE user_id = ?1)",
            nativeQuery = true
    )
    List<Activity> getEnrolledActivities(User u);

    @Query(value = "SELECT DISTINCT interest FROM interest", nativeQuery = true)
    List<String> getDistinctInterests();

    @Query(value = "SELECT DISTINCT activity.location_city FROM activity", nativeQuery = true)
    List<String> getDistinctCities();

}
