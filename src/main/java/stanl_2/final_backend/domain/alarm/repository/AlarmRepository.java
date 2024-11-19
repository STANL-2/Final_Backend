package stanl_2.final_backend.domain.alarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanl_2.final_backend.domain.alarm.aggregate.entity.Alarm;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, String> {
}
