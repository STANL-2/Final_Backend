package stanl_2.final_backend.domain.contract.command.application.service;

import stanl_2.final_backend.domain.contract.command.application.dto.request.ContractRegistRequestDTO;

public interface ContractService {
    void registerContract(ContractRegistRequestDTO contractRegistRequestDTO);
}
