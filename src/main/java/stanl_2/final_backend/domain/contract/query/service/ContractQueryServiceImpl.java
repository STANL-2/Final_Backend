package stanl_2.final_backend.domain.contract.query.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.contract.common.exception.ContractCommonException;
import stanl_2.final_backend.domain.contract.common.exception.ContractErrorCode;
import stanl_2.final_backend.domain.contract.query.dto.ContractSearchDTO;
import stanl_2.final_backend.domain.contract.query.dto.ContractSelectAllDTO;
import stanl_2.final_backend.domain.contract.query.dto.ContractSeletIdDTO;
import stanl_2.final_backend.domain.contract.query.repository.ContractMapper;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;

import java.util.List;

@Slf4j
@Service("queryContractService")
public class ContractQueryServiceImpl implements ContractQueryService {

    private final ContractMapper contractMapper;
    private final AuthQueryService authQueryService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public ContractQueryServiceImpl(ContractMapper contractMapper, AuthQueryService authQueryService, @Qualifier("redisTemplate") RedisTemplate redisTemplate) {
        this.contractMapper = contractMapper;
        this.authQueryService = authQueryService;
        this.redisTemplate = redisTemplate;
    }

    // 영업사원 조회
    // 계약서 전체조회
    @Override
    @Transactional(readOnly = true)
    public Page<ContractSelectAllDTO> selectAllContractEmployee(ContractSelectAllDTO contractSelectAllDTO, Pageable pageable) {

        String memberId = authQueryService.selectMemberIdByLoginId(contractSelectAllDTO.getMemberId());

        int offset = Math.toIntExact(pageable.getOffset());
        int pageSize = pageable.getPageSize();

        String caschKey = "myCache::contracts::offset=" + offset + "::pageSize=" + pageSize;

        List<ContractSelectAllDTO> contracts = (List<ContractSelectAllDTO>) redisTemplate.opsForValue().get(caschKey);

        if (contracts == null) {
            contracts = contractMapper.findContractAllByMemId(offset, pageSize, memberId);

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

        // 이스케이프된 HTML 제거
        String unescapedHtml = StringEscapeUtils.unescapeJson(responseContract.getCreatedUrl());
        responseContract.setCreatedUrl(unescapedHtml);

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

    // 영업담당자, 관리자 조회
    @Override
    @Transactional(readOnly = true)
    public Page<ContractSelectAllDTO> selectAllContract(ContractSelectAllDTO contractSelectAllDTO, Pageable pageable) {

        int offset = Math.toIntExact(pageable.getOffset());
        int pageSize = pageable.getPageSize();

        String caschKey = "myCache::contracts::offset=" + offset + "::pageSize=" + pageSize;

        List<ContractSelectAllDTO> contracts = (List<ContractSelectAllDTO>) redisTemplate.opsForValue().get(caschKey);

        if (contracts == null) {
            contracts = contractMapper.findContractAll(offset, pageSize);

            if (contracts == null) {
                throw new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND);
            }

            redisTemplate.opsForValue().set(caschKey, contracts);
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

        // 이스케이프된 HTML 제거
        String unescapedHtml = StringEscapeUtils.unescapeJson(responseContract.getCreatedUrl());
        responseContract.setCreatedUrl(unescapedHtml);

        return responseContract;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContractSearchDTO> selectBySearch(ContractSearchDTO contractSearchDTO, Pageable pageable) {

        int offset = Math.toIntExact(pageable.getOffset());
        int pageSize = pageable.getPageSize();
        List<ContractSearchDTO> contracts = contractMapper.findContractBySearch(offset, pageSize, contractSearchDTO);

        if (contracts == null) {
            throw new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND);
        }

        Integer count = contractMapper.findContractBySearchCount(contractSearchDTO);
        int totalContract = (count != null) ? count : 0;

        return new PageImpl<>(contracts, pageable, totalContract);
    }
}