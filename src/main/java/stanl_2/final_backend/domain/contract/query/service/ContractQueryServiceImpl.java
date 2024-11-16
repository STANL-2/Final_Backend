package stanl_2.final_backend.domain.contract.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.contract.common.exception.ContractCommonException;
import stanl_2.final_backend.domain.contract.common.exception.ContractErrorCode;
import stanl_2.final_backend.domain.contract.query.dto.ContractSearchDTO;
import stanl_2.final_backend.domain.contract.query.dto.ContractSelectAllDTO;
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
    public Page<ContractSelectAllDTO> selectAll(String memId, Pageable pageable) {

        Map<String, Object> params = new HashMap<>();
        params.put("memId", memId);
        params.put("offset", pageable.getOffset());
        params.put("pageSize", pageable.getPageSize());

        List<ContractSelectAllDTO> contractList = contractMapper.findContractAllByMemId(params);

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
    public Page<ContractSearchDTO> selectBySearch(ContractSearchDTO contractSearchDTO, Pageable pageable) {

        Map<String, Object> map = new HashMap<>();
        map.put("memberId", contractSearchDTO.getMemberId());
        map.put("memId", contractSearchDTO.getMemId());
        map.put("centerId", contractSearchDTO.getCenterId());
        map.put("name", contractSearchDTO.getName());
        map.put("startAt", contractSearchDTO.getStartAt());
        map.put("endAt", contractSearchDTO.getEndAt());
        map.put("customerName", contractSearchDTO.getCustomerName());
        map.put("customerClassifcation", contractSearchDTO.getCustomerClassifcation());
        map.put("productId", contractSearchDTO.getProductId());
        map.put("status", contractSearchDTO.getStatus());
        map.put("companyName", contractSearchDTO.getCompanyName());
        map.put("customerPurchaseCondition", contractSearchDTO.getCustomerPurchaseCondition());
        map.put("pageSize", pageable.getPageSize());
        map.put("offset", pageable.getOffset());

        List<ContractSearchDTO> contracts = contractMapper.findContractBySearch(map);

        if (contracts == null || contracts.isEmpty()) {
            throw new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND);
        }

        int total = contractMapper.findContractBySearchCount(map);

        return new PageImpl<>(contracts, pageable, total);
    }


}
