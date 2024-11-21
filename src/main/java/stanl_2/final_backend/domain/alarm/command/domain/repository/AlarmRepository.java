package stanl_2.final_backend.domain.alarm.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanl_2.final_backend.domain.alarm.command.domain.aggregate.entity.Alarm;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, String> {
    Alarm findByAlarmId(String alarmId);
}
