package stanl_2.final_backend.domain.contract.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stanl_2.final_backend.domain.contract.command.domain.aggregate.entity.Contract;

public interface ContractRepository extends JpaRepository<Contract, String> {
}
