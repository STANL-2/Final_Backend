package stanl_2.final_backend.domain.contract.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stanl_2.final_backend.domain.contract.command.domain.aggregate.entity.UpdateHistory;

public interface UpdateHistoryRepository extends JpaRepository<UpdateHistory, String> {
}
