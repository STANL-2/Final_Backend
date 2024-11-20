package stanl_2.final_backend.domain.contract.command.application.service;

import stanl_2.final_backend.domain.contract.command.application.dto.ContractDeleteDTO;
import stanl_2.final_backend.domain.contract.command.application.dto.ContractModifyDTO;
import stanl_2.final_backend.domain.contract.command.application.dto.ContractRegistDTO;
import stanl_2.final_backend.domain.contract.command.application.dto.ContractStatusModifyDTO;

import java.security.GeneralSecurityException;

public interface ContractCommandService {
    void registerContract(ContractRegistDTO contractRegistRequestDTO) throws GeneralSecurityException;

    ContractModifyDTO modifyContract(ContractModifyDTO contractModifyRequestDTO);

    void deleteContract(ContractDeleteDTO contractDeleteDTO);

    void modifyContractStatus(ContractStatusModifyDTO contractStatusModifyDTO);
}
