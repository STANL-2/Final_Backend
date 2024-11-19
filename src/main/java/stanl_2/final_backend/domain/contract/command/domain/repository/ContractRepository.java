package stanl_2.final_backend.domain.contract.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stanl_2.final_backend.domain.contract.command.domain.aggregate.entity.Contract;

import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, String> {
    Optional<Object> findByContractIdAndMemberId(String contractId, String memberId);

    Contract findByContractId(String contractId);
}
