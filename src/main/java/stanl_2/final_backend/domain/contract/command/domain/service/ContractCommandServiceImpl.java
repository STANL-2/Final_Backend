package stanl_2.final_backend.domain.contract.command.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.contract.command.application.dto.ContractModifyDTO;
import stanl_2.final_backend.domain.contract.command.application.dto.ContractRegistDTO;
import stanl_2.final_backend.domain.contract.command.application.service.ContractCommandService;
import stanl_2.final_backend.domain.contract.command.domain.aggregate.entity.Contract;
import stanl_2.final_backend.domain.contract.command.domain.repository.ContractRepository;
import stanl_2.final_backend.domain.contract.common.exception.ContractCommonException;
import stanl_2.final_backend.domain.contract.common.exception.ContractErrorCode;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service("contractServiceImpl")
public class ContractCommandServiceImpl implements ContractCommandService {

    private final ContractRepository contractRepository;
    private final ModelMapper modelMapper;

    public ContractCommandServiceImpl(ContractRepository contractRepository, ModelMapper modelMapper) {
        this.contractRepository = contractRepository;
        this.modelMapper = modelMapper;
    }

    private String  getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    @Transactional
    public void registerContract(ContractRegistDTO contractRegistRequestDTO) {
        // 회원인지 확인여부 및 값 가져오기
        
        // 일련번호로 제품테이블의 총식별번호 찾아서 제품 가져오기

        // 고객전화번호로 고객테이블 찾아서 고객이 있으면 넘어가고, 고객이 없으면 고객테이블에 고객 정보 넣기

        Contract contract = modelMapper.map(contractRegistRequestDTO, Contract.class);

        contract.setCentId("CEN_000000001");    // 회원의 매장번호 넣기
        contract.setProdId("PRO_000000001");    // 제품 번호 넣기

        contractRepository.save(contract);
    }

    @Override
    @Transactional
    public ContractModifyDTO modifyContract(ContractModifyDTO contractModifyRequestDTO) {

        // 회원인지 확인여부 및 값 가져오기

        // 일련번호로 제품테이블의 총식별번호 찾아서 제품 가져오기

        // 가져온 제품 정보에 수정된 값 넣기

        // 고객전화번호로 고객테이블 찾아서 가져오기

        // 가져온 고객 정보에 수정된 값 넣기

        Contract contract = contractRepository.findById(contractModifyRequestDTO.getId())
                .orElseThrow(() -> new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND));

        Contract updateContract = modelMapper.map(contractModifyRequestDTO, Contract.class);
        updateContract.setCreatedAt(contract.getCreatedAt());
        updateContract.setUpdatedAt(contract.getUpdatedAt());
        updateContract.setActive(contract.isActive());
        updateContract.setCentId(contract.getCentId());
        updateContract.setProdId(contract.getProdId());
        updateContract.setCreatedUrl(contract.getCreatedUrl());

        contractRepository.save(updateContract);

        ContractModifyDTO contractModifyDTO = modelMapper.map(updateContract, ContractModifyDTO.class);

        return contractModifyDTO;
    }

    @Override
    @Transactional
    public void deleteContract(String id) {

        // 회원 확인

        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND));

        contract.setActive(false);
        contract.setDeletedAt(getCurrentTime());

        contractRepository.save(contract);
    }
}