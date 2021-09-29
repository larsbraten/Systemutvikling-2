package v5.gidd.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import v5.gidd.entities.projection.LeaderboardUserModel;
import v5.gidd.entities.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findUserByCredentialsEmail(String email);


    @Query(
            value = "SELECT u.id AS id, u.first_name AS firstName, u.last_name as surName, u.points AS points FROM user u ORDER BY points DESC LIMIT 50",
            nativeQuery = true
    )
    List<LeaderboardUserModel> listTopFiftyUsers();
}

