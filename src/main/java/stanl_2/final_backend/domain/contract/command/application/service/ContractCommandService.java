package stanl_2.final_backend.domain.contract.command.application.service;

import stanl_2.final_backend.domain.contract.command.application.dto.ContractModifyDTO;
import stanl_2.final_backend.domain.contract.command.application.dto.ContractRegistDTO;
import stanl_2.final_backend.domain.contract.command.application.dto.response.ContractModifyResponseDTO;

public interface ContractCommandService {
    void registerContract(ContractRegistDTO contractRegistRequestDTO);

    ContractModifyDTO modifyContract(ContractModifyDTO contractModifyRequestDTO);

    void deleteContract(String id);
}
