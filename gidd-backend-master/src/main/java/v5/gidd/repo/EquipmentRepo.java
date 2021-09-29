package v5.gidd.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import v5.gidd.entities.equipment.Equipment;

import java.util.List;

@Repository
public interface EquipmentRepo extends JpaRepository<Equipment, Long> {

    @Query(
            value = "SELECT e FROM Equipment e WHERE e.activity = ?1"
    )
    List<Equipment> getEquipmentListByActivityId(Long activityId);
}
