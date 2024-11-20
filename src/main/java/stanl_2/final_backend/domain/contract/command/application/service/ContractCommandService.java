package stanl_2.final_backend.domain.contract.command.application.service;

import stanl_2.final_backend.domain.contract.command.application.dto.ContractDeleteDTO;
import stanl_2.final_backend.domain.contract.command.application.dto.ContractModifyDTO;
import stanl_2.final_backend.domain.contract.command.application.dto.ContractRegistDTO;
import stanl_2.final_backend.domain.contract.command.application.dto.ContractStatusModifyDTO;

public interface ContractCommandService {
    void registerContract(ContractRegistDTO contractRegistRequestDTO);

    ContractModifyDTO modifyContract(ContractModifyDTO contractModifyRequestDTO);

    void deleteContract(ContractDeleteDTO contractDeleteDTO);

    void modifyContractStatus(ContractStatusModifyDTO contractStatusModifyDTO);
}
