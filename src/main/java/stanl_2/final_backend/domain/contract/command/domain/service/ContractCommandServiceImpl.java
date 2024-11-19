package stanl_2.final_backend.domain.contract.command.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.contract.command.application.dto.ContractDeleteDTO;
import stanl_2.final_backend.domain.contract.command.application.dto.ContractModifyDTO;
import stanl_2.final_backend.domain.contract.command.application.dto.ContractRegistDTO;
import stanl_2.final_backend.domain.contract.command.application.dto.ContractStatusModifyDTO;
import stanl_2.final_backend.domain.contract.command.application.service.ContractCommandService;
import stanl_2.final_backend.domain.contract.command.domain.aggregate.entity.Contract;
import stanl_2.final_backend.domain.contract.command.domain.repository.ContractRepository;
import stanl_2.final_backend.domain.contract.common.exception.ContractCommonException;
import stanl_2.final_backend.domain.contract.common.exception.ContractErrorCode;
import stanl_2.final_backend.domain.member.query.dto.MemberDTO;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.domain.product.query.dto.ProductSelectIdDTO;
import stanl_2.final_backend.domain.product.query.service.ProductService;
import stanl_2.final_backend.global.exception.GlobalCommonException;
import stanl_2.final_backend.global.exception.GlobalErrorCode;

import java.security.GeneralSecurityException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service("contractServiceImpl")
public class ContractCommandServiceImpl implements ContractCommandService {

    private final ContractRepository contractRepository;
    private final AuthQueryService authQueryService;
    private final MemberQueryService memberQueryService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    public ContractCommandServiceImpl(ContractRepository contractRepository, AuthQueryService authQueryService, MemberQueryService memberQueryService, ProductService productService, ModelMapper modelMapper) {
        this.contractRepository = contractRepository;
        this.authQueryService = authQueryService;
        this.memberQueryService = memberQueryService;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    private String  getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    @Transactional
    public void registerContract(ContractRegistDTO contractRegistRequestDTO) throws GeneralSecurityException {

        if(contractRegistRequestDTO.getRoles().stream()
                .anyMatch(role -> "ROLE_EMPLOYEE".equals(role.getAuthority()))){

            // 영업사원 번호
            String memberId = authQueryService.selectMemberIdByLoginId(contractRegistRequestDTO.getMemberId());

            // 일련번호로 제품테이블의 총식별번호 찾아서 제품 가져오기
//            ProductSelectIdDTO productSelectIdDTO = productService.selectByProductSerialNumber(contractRegistRequestDTO.getSerialNum());
//
//            System.out.println("제품!!!!!!!!!!: " + productSelectIdDTO);
//            String productId = productSelectIdDTO.getId();

            // 판매내역 업로드

            // 제품 재고 수 줄이기

            // 고객전화번호로 고객테이블 찾아서 고객이 있으면 넘어가고, 고객이 없으면 고객테이블에 고객 정보 넣기

            // 회원의 영업 매장번호
//            MemberDTO memberDTO = memberQueryService.selectMemberInfo(contractRegistRequestDTO.getMemberId());
//            String centerId = memberDTO.getCenterId();

            Contract contract = modelMapper.map(contractRegistRequestDTO, Contract.class);

            contract.setMemberId(memberId);
            contract.setCenterId("CEN_000000001");    // 회원의 매장번호 넣기
            contract.setProductId("PRO_000000001");    // 제품 번호 넣기
            contract.setCustomerId("CUS_000000001");

            contractRepository.save(contract);
        } else {
            throw new GlobalCommonException(GlobalErrorCode.UNAUTHORIZED);
        }
    }

    @Override
    @Transactional
    public ContractModifyDTO modifyContract(ContractModifyDTO contractModifyRequestDTO) {

        if(contractModifyRequestDTO.getRoles().stream()
                .anyMatch(role -> "ROLE_EMPLOYEE".equals(role.getAuthority()))) {

            String memberId = authQueryService.selectMemberIdByLoginId(contractModifyRequestDTO.getMemberId());

            // 일련번호로 제품테이블의 총식별번호 찾아서 제품 가져오기
//            ProductSelectIdDTO productSelectIdDTO = productService.selectByProductSerialNumber(contractModifyRequestDTO.getSerialNum());
//            String productId = productSelectIdDTO.getId();
            contractModifyRequestDTO.setProductId("PRO_000000001");

            // 판매내역 수정

            // 고객전화번호로 고객테이블 찾아서 가져오기

            // 가져온 고객 정보에 수정된 값 넣기

            Contract contract = (Contract) contractRepository.findByContractIdAndMemberId(contractModifyRequestDTO.getContractId(), memberId)
                    .orElseThrow(() -> new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND));

            Contract updateContract = modelMapper.map(contractModifyRequestDTO, Contract.class);
            updateContract.setCreatedAt(contract.getCreatedAt());
            updateContract.setUpdatedAt(contract.getUpdatedAt());
            updateContract.setActive(contract.isActive());
            updateContract.setCenterId(contract.getCenterId());
            updateContract.setCreatedUrl(contract.getCreatedUrl());

            contractRepository.save(updateContract);

            ContractModifyDTO contractModifyDTO = modelMapper.map(updateContract, ContractModifyDTO.class);

            return contractModifyDTO;
        } else {
            throw new GlobalCommonException(GlobalErrorCode.UNAUTHORIZED);
        }


    }

    @Override
    @Transactional
    public void deleteContract(ContractDeleteDTO contractDeleteDTO) {

        if(contractDeleteDTO.getRoles().stream()
                .anyMatch(role -> "ROLE_EMPLOYEE".equals(role.getAuthority()))) {

            String memberId = authQueryService.selectMemberIdByLoginId(contractDeleteDTO.getMemberId());

            Contract contract = (Contract) contractRepository.findByContractIdAndMemberId(contractDeleteDTO.getContractId(), memberId)
                    .orElseThrow(() -> new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND));

            contract.setActive(false);
            contract.setDeletedAt(getCurrentTime());

            // 고객정보도 false

            // 판매내역도 false 해서 -1

            contractRepository.save(contract);

        } else {
            throw new GlobalCommonException(GlobalErrorCode.UNAUTHORIZED);
        }
    }

    @Override
    @Transactional
    public void modifyContractStatus(ContractStatusModifyDTO contractStatusModifyDTO) {
        // 역할 확인
        if (contractStatusModifyDTO.getRoles().stream()
                .anyMatch(role -> "ROLE_MANAGER".equals(role) || "ROLE_REPRESENTATIVE".equals(role))) {

            // 관리자 ID 조회
            String adminId = authQueryService.selectMemberIdByLoginId(contractStatusModifyDTO.getAdminId());

            // 계약 조회 및 수정
            Contract contract = contractRepository.findByContractId(contractStatusModifyDTO.getContractId());

            if (contract != null) {
                contract.setStatus(contractStatusModifyDTO.getStatus());
                contract.setAdminId(adminId);

                contractRepository.save(contract);
            }
        } else {
            throw new GlobalCommonException(GlobalErrorCode.UNAUTHORIZED);
        }
    }


}
