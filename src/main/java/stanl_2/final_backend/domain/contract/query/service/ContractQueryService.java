package stanl_2.final_backend.domain.contract.query.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stanl_2.final_backend.domain.contract.query.dto.ContractSearchDTO;
import stanl_2.final_backend.domain.contract.query.dto.ContractSelectAllDTO;
import stanl_2.final_backend.domain.contract.query.dto.ContractSeletIdDTO;

public interface ContractQueryService {

    Page<ContractSelectAllDTO> selectAllContract(ContractSelectAllDTO contractSelectAllDTO, Pageable pageable);

    ContractSeletIdDTO selectDetailContract(ContractSeletIdDTO contractDTO);

    Page<ContractSearchDTO> selectBySearch(ContractSearchDTO contractSearchDTO, Pageable pageable);

    Page<ContractSelectAllDTO> selectAllContractEmployee(ContractSelectAllDTO contractSelectAllDTO, Pageable pageable);

    ContractSeletIdDTO selectDetailContractEmployee(ContractSeletIdDTO contractSeletIdDTO);

    Page<ContractSearchDTO> selectBySearchEmployee(ContractSearchDTO contractSearchDTO, Pageable pageable);


}
