package stanl_2.final_backend.domain.contract.query.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
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
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.product.query.dto.ProductSelectIdDTO;
import stanl_2.final_backend.domain.product.query.service.ProductService;
import stanl_2.final_backend.global.exception.GlobalCommonException;
import stanl_2.final_backend.global.exception.GlobalErrorCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("queryContractService")
public class ContractQueryServiceImpl implements ContractQueryService {

    private final ContractMapper contractMapper;
    private final AuthQueryService authQueryService;

    @Autowired
    public ContractQueryServiceImpl(ContractMapper contractMapper, AuthQueryService authQueryService) {
        this.contractMapper = contractMapper;
        this.authQueryService = authQueryService;
    }

    // 계약서 전체조회
    @Override
    public Page<ContractSelectAllDTO> selectAll(ContractSelectAllDTO contractSelectAllDTO, Pageable pageable) {

        if(contractSelectAllDTO.getRoles().stream()
                .anyMatch(role -> "ROLE_EMPLOYEE".equals(role.getAuthority()))){

            String memberId = authQueryService.selectMemberIdByLoginId(contractSelectAllDTO.getMemberId());

            Map<String, Object> params = new HashMap<>();
            params.put("memId", memberId);
            params.put("offset", pageable.getOffset());
            params.put("pageSize", pageable.getPageSize());

            List<ContractSelectAllDTO> contractList = contractMapper.findContractAllByMemId(params);

            if (contractList == null || contractList.isEmpty()) {
                throw new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND);
            }

            int total = contractMapper.findContractCountByMemId(memberId);

            return new PageImpl<>(contractList, pageable, total);

        } else if (contractSelectAllDTO.getRoles().stream()
                .anyMatch(role -> "ROLE_MANAGER".equals(role.getAuthority()) || "ROLE_REPRESENTATIVE".equals(role.getAuthority()))) {

            Map<String, Object> params = new HashMap<>();
            params.put("offset", pageable.getOffset());
            params.put("pageSize", pageable.getPageSize());

            List<ContractSelectAllDTO> contractList = contractMapper.findContractAll(params);

            if (contractList == null || contractList.isEmpty()) {
                throw new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND);
            }

            int total = contractMapper.findContractCount();

            return new PageImpl<>(contractList, pageable, total);
        } else {
            throw new GlobalCommonException(GlobalErrorCode.UNAUTHORIZED);
        }
    }

    // 계약서 상세조회
    @Override
    public ContractSeletIdDTO selectDetailContract(ContractSeletIdDTO contractSeletIdDTO) {

        if(contractSeletIdDTO.getRoles().stream()
                .anyMatch(role -> "ROLE_MANAGER".equals(role.getAuthority()))){

            String memberId = authQueryService.selectMemberIdByLoginId(contractSeletIdDTO.getMemberId());
            contractSeletIdDTO.setMemberId(memberId);

            ContractSeletIdDTO responseContract = contractMapper.findContractByIdAndMemId(contractSeletIdDTO);

            if (responseContract == null) {
                throw new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND);
            }

            // 이스케이프된 HTML 제거
            String unescapedHtml = StringEscapeUtils.unescapeJson(responseContract.getCreatedUrl());
            responseContract.setCreatedUrl(unescapedHtml);

            return responseContract;

        } else if (contractSeletIdDTO.getRoles().stream()
                .anyMatch(role -> "ROLE_MANAGER".equals(role.getAuthority()) || "ROLE_REPRESENTATIVE".equals(role.getAuthority()))) {

            ContractSeletIdDTO responseContract = contractMapper.findContractById(contractSeletIdDTO.getContractId());

            if (responseContract == null) {
                throw new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND);
            }

            // 이스케이프된 HTML 제거
            String unescapedHtml = StringEscapeUtils.unescapeJson(responseContract.getCreatedUrl());
            responseContract.setCreatedUrl(unescapedHtml);

            return responseContract;

        } else {
            throw new GlobalCommonException(GlobalErrorCode.UNAUTHORIZED);
        }
    }

    @Override
    public Page<ContractSearchDTO> selectBySearch(ContractSearchDTO contractSearchDTO, Pageable pageable) {

        if (contractSearchDTO.getRoles().stream()
                .anyMatch(role -> "ROLE_MANAGER".equals(role.getAuthority()))) {

            String memberId = authQueryService.selectMemberIdByLoginId(contractSearchDTO.getMemberId());

            Map<String, Object> map = new HashMap<>();
            map.put("memberId", memberId);
            map.put("centerId", contractSearchDTO.getCenterId());
            map.put("title", contractSearchDTO.getTitle());
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

            List<ContractSearchDTO> contracts = contractMapper.findContractBySearchAndMemberId(map);

            if (contracts == null || contracts.isEmpty()) {
                throw new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND);
            }

            int total = contractMapper.findContractBySearchAndMemberIdCount(map);

            return new PageImpl<>(contracts, pageable, total);

        } else if (contractSearchDTO.getRoles().stream()
                .anyMatch(role -> "ROLE_MANAGER".equals(role.getAuthority()) || "ROLE_REPRESENTATIVE".equals(role.getAuthority()))) {

            Map<String, Object> map = new HashMap<>();
            map.put("searchMemberId", contractSearchDTO.getSearchMemberId());
            map.put("centerId", contractSearchDTO.getCenterId());
            map.put("title", contractSearchDTO.getTitle());
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

        } else {
            throw new GlobalCommonException(GlobalErrorCode.UNAUTHORIZED);
        }
    }
}
