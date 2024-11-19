package stanl_2.final_backend.domain.contract.query.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.contract.query.dto.ContractSearchDTO;
import stanl_2.final_backend.domain.contract.query.dto.ContractSelectAllDTO;
import stanl_2.final_backend.domain.contract.query.dto.ContractSeletIdDTO;

import java.util.Map;

public interface ContractQueryService {
    ContractSeletIdDTO selectDetailContract(ContractSeletIdDTO contractDTO);

    Page<ContractSearchDTO> selectBySearch(ContractSearchDTO contractSearchDTO, Pageable pageable);

    Page<ContractSelectAllDTO> selectAll(ContractSelectAllDTO contractSelectAllDTO, Pageable pageable);

    @Transactional(readOnly = true)
    ContractSeletIdDTO selectContractByIdAndMemberId(String contractId, String memberId);
}
