package stanl_2.final_backend.domain.contract.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.contract.common.exception.CommonException;
import stanl_2.final_backend.domain.contract.common.exception.ErrorCode;
import stanl_2.final_backend.domain.contract.query.dto.ContractDTO;
import stanl_2.final_backend.domain.contract.query.repository.ContractMapper;

@Service("queryContractService")
public class ContractServiceImpl implements ContractService {

    private final ContractMapper contractMapper;

    @Autowired
    public ContractServiceImpl(ContractMapper contractMapper) {
        this.contractMapper = contractMapper;
    }


    // 계약서 상세조회
    @Override
    public ContractDTO selectDetailContract(ContractDTO contractDTO) {

        ContractDTO responseContract = contractMapper.findContractByIdAndMemId(contractDTO);

        if (responseContract == null) {
            throw new CommonException(ErrorCode.CONTRACT_NOT_FOUND);
        }

        return responseContract;
    }
}
