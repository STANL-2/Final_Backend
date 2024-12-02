package stanl_2.final_backend.domain.contract.query.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.contract.common.exception.ContractCommonException;
import stanl_2.final_backend.domain.contract.common.exception.ContractErrorCode;
import stanl_2.final_backend.domain.contract.query.dto.ContractExcelDTO;
import stanl_2.final_backend.domain.contract.query.dto.ContractSearchDTO;
import stanl_2.final_backend.domain.contract.query.dto.ContractSelectAllDTO;
import stanl_2.final_backend.domain.contract.query.dto.ContractSeletIdDTO;
import stanl_2.final_backend.domain.contract.query.repository.ContractMapper;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.global.excel.ExcelUtilsV1;

import java.security.GeneralSecurityException;
import java.util.List;

@Slf4j
@Service("queryContractService")
public class ContractQueryServiceImpl implements ContractQueryService {

    private final ContractMapper contractMapper;
    private final AuthQueryService authQueryService;
    private final MemberQueryService memberQueryService;
    private final UpdateHistoryQueryService updateHistoryQueryService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ExcelUtilsV1 excelUtilsV1;

    @Autowired
    public ContractQueryServiceImpl(ContractMapper contractMapper, AuthQueryService authQueryService, MemberQueryService memberQueryService, UpdateHistoryQueryService updateHistoryQueryService, @Qualifier("redisTemplate") RedisTemplate redisTemplate, ExcelUtilsV1 excelUtilsV1) {
        this.contractMapper = contractMapper;
        this.authQueryService = authQueryService;
        this.memberQueryService = memberQueryService;
        this.updateHistoryQueryService = updateHistoryQueryService;
        this.redisTemplate = redisTemplate;
        this.excelUtilsV1 = excelUtilsV1;
    }

    // 영업사원 조회
    // 계약서 전체조회
    @Override
    @Transactional(readOnly = true)
    public Page<ContractSelectAllDTO> selectAllContractEmployee(ContractSelectAllDTO contractSelectAllDTO, Pageable pageable) {

        String memberId = authQueryService.selectMemberIdByLoginId(contractSelectAllDTO.getMemberId());

        int offset = Math.toIntExact(pageable.getOffset());
        int pageSize = pageable.getPageSize();

        // 정렬 정보 가져오기
        Sort sort = pageable.getSort();
        String sortField = null;
        String sortOrder = null;
        if (sort.isSorted()) {
            sortField = sort.iterator().next().getProperty();
            sortOrder = sort.iterator().next().isAscending() ? "ASC" : "DESC";
        }

        String caschKey = "myCache::contracts::offset=" + offset + "::pageSize=" + pageSize;

        List<ContractSelectAllDTO> contracts = (List<ContractSelectAllDTO>) redisTemplate.opsForValue().get(caschKey);

        if (contracts == null) {
            contracts = contractMapper.findContractAllByMemId(offset, pageSize, memberId, sortField, sortOrder);

            if (contracts == null) {
                throw new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND);
            }

            redisTemplate.opsForValue().set(caschKey, contracts);
        }

        Integer count = contractMapper.findContractCountByMemId(memberId);
        int totalContract = (count != null) ? count : 0;

        return new PageImpl<>(contracts, pageable, totalContract);
    }

    // 계약서 상세조회
    @Override
    @Transactional(readOnly = true)
    public ContractSeletIdDTO selectDetailContractEmployee(ContractSeletIdDTO contractSeletIdDTO) {

        String memberId = authQueryService.selectMemberIdByLoginId(contractSeletIdDTO.getMemberId());

        ContractSeletIdDTO responseContract = contractMapper.findContractByIdAndMemId(contractSeletIdDTO.getContractId(), memberId);

        if (responseContract == null) {
            throw new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND);
        }

        String content = updateHistoryQueryService.selectUpdateHistoryByContractId(responseContract.getContractId());

        if (content == null) {
            String unescapedHtml = StringEscapeUtils.unescapeJson(responseContract.getCreatedUrl());
            responseContract.setCreatedUrl(unescapedHtml);
        }
        responseContract.setCreatedUrl(content);

        return responseContract;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContractSearchDTO> selectBySearchEmployee(ContractSearchDTO contractSearchDTO, Pageable pageable) {
        String memberId = authQueryService.selectMemberIdByLoginId(contractSearchDTO.getMemberId());
        contractSearchDTO.setMemberId(memberId);

        int offset = Math.toIntExact(pageable.getOffset());
        int pageSize = pageable.getPageSize();
        List<ContractSearchDTO> contracts = contractMapper.findContractBySearchAndMemberId(offset, pageSize, contractSearchDTO);

        if (contracts == null) {
            throw new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND);
        }

        Integer count = contractMapper.findContractBySearchAndMemberIdCount(contractSearchDTO);
        int totalContract = (count != null) ? count : 0;

        return new PageImpl<>(contracts, pageable, totalContract);
    }

    // 영업 관리자 조회
    @Override
    @Transactional(readOnly = true)
    public Page<ContractSelectAllDTO> selectAllContractAdmin(ContractSelectAllDTO contractSelectAllDTO, Pageable pageable) throws GeneralSecurityException {
        String centerId = memberQueryService.selectMemberInfo(contractSelectAllDTO.getMemberId()).getCenterId();

        int offset = Math.toIntExact(pageable.getOffset());
        int pageSize = pageable.getPageSize();

        // 정렬 정보 가져오기
        Sort sort = pageable.getSort();
        String sortField = null;
        String sortOrder = null;
        if (sort.isSorted()) {
            sortField = sort.iterator().next().getProperty();
            sortOrder = sort.iterator().next().isAscending() ? "ASC" : "DESC";
        }

        String caschKey = "myCache::contracts::offset=" + offset + "::pageSize=" + pageSize;

        List<ContractSelectAllDTO> contracts = (List<ContractSelectAllDTO>) redisTemplate.opsForValue().get(caschKey);

        if (contracts == null) {
            contracts = contractMapper.findContractAllByCenterId(offset, pageSize, centerId, sortField, sortOrder);

            if (contracts == null) {
                throw new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND);
            }

            redisTemplate.opsForValue().set(caschKey, contracts);
        }

        Integer count = contractMapper.findContractCountByCenterId(centerId);
        int totalContract = (count != null) ? count : 0;

        return new PageImpl<>(contracts, pageable, totalContract);
    }

    @Override
    @Transactional(readOnly = true)
    public ContractSeletIdDTO selectDetailContractAdmin(ContractSeletIdDTO contractSeletIdDTO) throws GeneralSecurityException {
        String centerId = memberQueryService.selectMemberInfo(contractSeletIdDTO.getMemberId()).getCenterId();

        ContractSeletIdDTO responseContract = contractMapper.findContractByIdAndCenterId(contractSeletIdDTO.getContractId(), centerId);

        // 이스케이프된 HTML 제거
        String unescapedHtml = StringEscapeUtils.unescapeJson(responseContract.getCreatedUrl());
        responseContract.setCreatedUrl(unescapedHtml);

        return responseContract;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContractSearchDTO> selectBySearchAdmin(ContractSearchDTO contractSearchDTO, Pageable pageable) throws GeneralSecurityException {
        String centerId = memberQueryService.selectMemberInfo(contractSearchDTO.getMemberId()).getCenterId();

        int offset = Math.toIntExact(pageable.getOffset());
        int pageSize = pageable.getPageSize();
        List<ContractSearchDTO> contracts = contractMapper.findContractBySearchAndCenterId(offset, pageSize, contractSearchDTO, centerId);

        if (contracts == null) {
            throw new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND);
        }

        Integer count = contractMapper.findContractBySearchAndCenterCount(contractSearchDTO, centerId);
        int totalContract = (count != null) ? count : 0;

        return new PageImpl<>(contracts, pageable, totalContract);
    }

    // 영업담당자 조회
    @Override
    @Transactional(readOnly = true)
    public Page<ContractSelectAllDTO> selectAllContract(ContractSelectAllDTO contractSelectAllDTO, Pageable pageable) {

        int offset = Math.toIntExact(pageable.getOffset());
        int pageSize = pageable.getPageSize();

        // 정렬 정보 가져오기
        Sort sort = pageable.getSort();
        String sortField = null;
        String sortOrder = null;
        if (sort.isSorted()) {
            sortField = sort.iterator().next().getProperty();
            sortOrder = sort.iterator().next().isAscending() ? "ASC" : "DESC";
        }

        List<ContractSelectAllDTO> contracts = contractMapper.findContractAll(offset, pageSize, sortField, sortOrder);

        if (contracts == null) {
            throw new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND);
        }

        Integer count = contractMapper.findContractCount();
        int totalContract = (count != null) ? count : 0;

        return new PageImpl<>(contracts, pageable, totalContract);
    }

    // 계약서 상세조회
    @Override
    @Transactional(readOnly = true)
    public ContractSeletIdDTO selectDetailContract(ContractSeletIdDTO contractSeletIdDTO) {

        ContractSeletIdDTO responseContract = contractMapper.findContractById(contractSeletIdDTO.getContractId());

        if (responseContract == null) {
            throw new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND);
        }

        String content = updateHistoryQueryService.selectUpdateHistoryByContractId(responseContract.getContractId());

        if (content == null) {
            String unescapedHtml = StringEscapeUtils.unescapeJson(responseContract.getCreatedUrl());
            responseContract.setCreatedUrl(unescapedHtml);
            return responseContract;
        }
        responseContract.setCreatedUrl(content);

        return responseContract;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContractSearchDTO> selectBySearch(ContractSearchDTO contractSearchDTO, Pageable pageable) {

        int offset = Math.toIntExact(pageable.getOffset());
        int pageSize = pageable.getPageSize();

        // 정렬 정보 가져오기
        Sort sort = pageable.getSort();
        String sortField = null;
        String sortOrder = null;
        if (sort.isSorted()) {
            sortField = sort.iterator().next().getProperty();
            sortOrder = sort.iterator().next().isAscending() ? "ASC" : "DESC";
        }

        List<ContractSearchDTO> contracts = contractMapper.findContractBySearch(offset, pageSize, contractSearchDTO, sortField, sortOrder);

        if (contracts == null) {
            throw new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND);
        }

        int totalContract = contractMapper.findContractBySearchCount(contractSearchDTO);

        if (totalContract == 0) {
            throw new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND);
        }

        return new PageImpl<>(contracts, pageable, totalContract);
    }

    @Override
    @Transactional(readOnly = true)
    public void exportContractToExcel(HttpServletResponse response) {
        List<ContractExcelDTO> contractExcels = contractMapper.findContractForExcel();

        if (contractExcels == null) {
            throw new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND);
        }

        excelUtilsV1.download(ContractExcelDTO.class, contractExcels, "contractExcel", response);
    }

}