package stanl_2.final_backend.domain.schedule.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanl_2.final_backend.domain.schedule.command.domain.aggregate.entity.Schedule;

import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, String> {

    Optional<Schedule> findByScheduleId(String scheduleId);
}
