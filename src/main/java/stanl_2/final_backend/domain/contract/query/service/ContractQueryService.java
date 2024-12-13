package stanl_2.final_backend.domain.contract.query.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stanl_2.final_backend.domain.contract.query.dto.ContractSearchDTO;
import stanl_2.final_backend.domain.contract.query.dto.ContractSelectAllDTO;
import stanl_2.final_backend.domain.contract.query.dto.ContractSeletIdDTO;

import java.security.GeneralSecurityException;

public interface ContractQueryService {

    Page<ContractSelectAllDTO> selectAllContract(ContractSelectAllDTO contractSelectAllDTO, Pageable pageable);

    ContractSeletIdDTO selectDetailContract(ContractSeletIdDTO contractDTO);

    Page<ContractSearchDTO> selectBySearch(ContractSearchDTO contractSearchDTO, Pageable pageable);

    Page<ContractSelectAllDTO> selectAllContractEmployee(ContractSelectAllDTO contractSelectAllDTO, Pageable pageable);

    ContractSeletIdDTO selectDetailContractEmployee(ContractSeletIdDTO contractSeletIdDTO);

    Page<ContractSearchDTO> selectBySearchEmployee(ContractSearchDTO contractSearchDTO, Pageable pageable);


    Page<ContractSelectAllDTO> selectAllContractAdmin(ContractSelectAllDTO contractSelectAllDTO, Pageable pageable) throws GeneralSecurityException;

    ContractSeletIdDTO selectDetailContractAdmin(ContractSeletIdDTO contractSeletIdDTO) throws GeneralSecurityException;

    Page<ContractSearchDTO> selectBySearchAdmin(ContractSearchDTO contractSearchDTO, Pageable pageable) throws GeneralSecurityException;


    void exportContractToExcel(HttpServletResponse response);
}
