package v5.gidd.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import v5.gidd.entities.activity.waitList.ActivityWaitList;
import v5.gidd.entities.activity.waitList.WaitlistId;

@Repository
public interface WaitlistRepo extends JpaRepository<ActivityWaitList, WaitlistId> {}
