package stanl_2.final_backend.domain.contract.command.application.service;

import stanl_2.final_backend.domain.contract.command.application.dto.request.ContractModifyRequestDTO;
import stanl_2.final_backend.domain.contract.command.application.dto.request.ContractRegistRequestDTO;
import stanl_2.final_backend.domain.contract.command.application.dto.response.ContractModifyResponseDTO;

public interface ContractService {
    void registerContract(ContractRegistRequestDTO contractRegistRequestDTO);

    ContractModifyResponseDTO modifyContract(ContractModifyRequestDTO contractModifyRequestDTO);

    void deleteContract(String id);
}
