package stanl_2.final_backend.domain.contract.query.service;

import stanl_2.final_backend.domain.contract.query.dto.ContractDTO;

public interface ContractService {
    ContractDTO selectDetailContract(ContractDTO contractDTO);
}
