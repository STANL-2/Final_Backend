package stanl_2.final_backend.domain.contract.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.center.common.util.RequestList;
import stanl_2.final_backend.domain.contract.common.exception.ContractCommonException;
import stanl_2.final_backend.domain.contract.common.exception.ContractErrorCode;
import stanl_2.final_backend.domain.contract.query.dto.ContractSeletIdDTO;
import stanl_2.final_backend.domain.contract.query.repository.ContractMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("queryContractService")
public class ContractQueryServiceImpl implements ContractQueryService {

    private final ContractMapper contractMapper;

    @Autowired
    public ContractQueryServiceImpl(ContractMapper contractMapper) {
        this.contractMapper = contractMapper;
    }

    // 계약서 전체조회
    @Override
    public Page<Map<String, Object>> selectAll(String memId, Pageable pageable) {

        Map<String, Object> params = new HashMap<>();
        params.put("memId", memId);
        params.put("offset", pageable.getOffset());
        params.put("pageSize", pageable.getPageSize());

        List<Map<String, Object>> contractList = contractMapper.findContractAllByMemId(params);

        if (contractList == null || contractList.isEmpty()) {
            throw new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND);
        }

        int total = contractMapper.findContractCountByMemId(memId);

        return new PageImpl<>(contractList, pageable, total);
    }

    // 계약서 상세조회
    @Override
    public ContractSeletIdDTO selectDetailContract(ContractSeletIdDTO contractDTO) {

        ContractSeletIdDTO responseContract = contractMapper.findContractByIdAndMemId(contractDTO);

        if (responseContract == null) {
            throw new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND);
        }

        return responseContract;
    }

    @Override
    public Page<Map<String, Object>> selectBySearch(Map<String, Object> paramMap) {
        Pageable pageable = (Pageable) paramMap.get("pageable");

        List<Map<String, Object>> contractList = contractMapper.findContractBySearch(paramMap);

        if (contractList == null || contractList.isEmpty()) {
            throw new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND);
        }

        int total = contractMapper.findContractBySearchCount(paramMap);

        return new PageImpl<>(contractList, pageable, total);
    }


}
