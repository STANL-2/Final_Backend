package stanl_2.final_backend.domain.log.command.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanl_2.final_backend.domain.log.command.aggregate.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
}