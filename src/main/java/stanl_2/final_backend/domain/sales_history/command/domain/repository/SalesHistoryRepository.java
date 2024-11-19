package stanl_2.final_backend.domain.sales_history.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanl_2.final_backend.domain.sales_history.command.domain.aggregate.entity.SalesHistory;

@Repository
public interface SalesHistoryRepository extends JpaRepository<SalesHistory, String> {
}
