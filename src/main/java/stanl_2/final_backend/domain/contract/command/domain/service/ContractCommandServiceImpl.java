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
import stanl_2.final_backend.domain.customer.command.application.dto.CustomerModifyDTO;
import stanl_2.final_backend.domain.customer.command.application.dto.CustomerRegistDTO;
import stanl_2.final_backend.domain.customer.command.application.service.CustomerCommandService;
import stanl_2.final_backend.domain.customer.query.dto.CustomerDTO;
import stanl_2.final_backend.domain.customer.query.service.CustomerQueryService;
import stanl_2.final_backend.domain.member.query.dto.MemberDTO;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.domain.product.command.application.command.service.ProductCommandService;
import stanl_2.final_backend.domain.product.query.dto.ProductSelectIdDTO;
import stanl_2.final_backend.domain.product.query.service.ProductQueryService;
import stanl_2.final_backend.domain.sales_history.command.application.service.SalesHistoryCommandService;
import stanl_2.final_backend.global.utils.AESUtils;

import java.security.GeneralSecurityException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service("contractServiceImpl")
public class ContractCommandServiceImpl implements ContractCommandService {

    private final ContractRepository contractRepository;
    private final AuthQueryService authQueryService;
    private final CustomerQueryService customerQueryService;
    private final MemberQueryService memberQueryService;
    private final CustomerCommandService customerCommandService;
    private final ProductQueryService productService;
    private final ProductCommandService productCommandService;
    private final SalesHistoryCommandService salesHistoryCommandService;
    private final ModelMapper modelMapper;
    private final AESUtils aesUtils;

    public ContractCommandServiceImpl(ContractRepository contractRepository, AuthQueryService authQueryService, CustomerQueryService customerQueryService, MemberQueryService memberQueryService, CustomerCommandService customerCommandService, ProductQueryService productService, ProductCommandService productCommandService, SalesHistoryCommandService salesHistoryCommandService, ModelMapper modelMapper, AESUtils aesUtils) {
        this.contractRepository = contractRepository;
        this.authQueryService = authQueryService;
        this.customerQueryService = customerQueryService;
        this.memberQueryService = memberQueryService;
        this.customerCommandService = customerCommandService;
        this.productService = productService;
        this.productCommandService = productCommandService;
        this.salesHistoryCommandService = salesHistoryCommandService;
        this.modelMapper = modelMapper;
        this.aesUtils = aesUtils;
    }

    private String  getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private String handleCustomerInfo(ContractRegistDTO contractRegistRequestDTO, String memberId) throws GeneralSecurityException {
        // 고객 정보 조회
        CustomerDTO customerDTO = customerQueryService.selectCustomerInfoByPhone(contractRegistRequestDTO.getCustomerPhone());

        // 고객이 없으면 새로 등록
        if (customerDTO == null) {
            CustomerRegistDTO customerRegistDTO = new CustomerRegistDTO();
            customerRegistDTO.setName(contractRegistRequestDTO.getCustomerName());
            customerRegistDTO.setAge(contractRegistRequestDTO.getCustomerAge());
            customerRegistDTO.setPhone(contractRegistRequestDTO.getCustomerPhone());
            customerRegistDTO.setEmail(contractRegistRequestDTO.getCustomerEmail());
            customerRegistDTO.setMemberId(memberId);

            customerCommandService.registerCustomerInfo(customerRegistDTO);

            // 등록 후 고객 정보 재조회
            customerDTO = customerQueryService.selectCustomerInfoByPhone(contractRegistRequestDTO.getCustomerPhone());
        }
        return customerDTO.getCustomerId();
    }

    @Override
    @Transactional
    public void registerContract(ContractRegistDTO contractRegistRequestDTO) throws GeneralSecurityException {

        // 영업사원 번호
        String memberId = authQueryService.selectMemberIdByLoginId(contractRegistRequestDTO.getMemberId());

        // 일련번호로 제품테이블의 총식별번호 찾아서 제품 가져오기
        ProductSelectIdDTO productSelectIdDTO = productService.selectByProductSerialNumber(contractRegistRequestDTO.getSerialNum());
        String productId = productSelectIdDTO.getId();

        String customerId = null; // 고객 ID 변수 선언
        String centerId = null;   // 매장 ID 변수 선언

        // 고객전화번호로 고객테이블 찾아서 고객이 있으면 넘어가고, 고객이 없으면 고객테이블에 고객 정보 넣기
        customerId = handleCustomerInfo(contractRegistRequestDTO, memberId);

        // 회원의 영업 매장번호
        MemberDTO memberDTO = memberQueryService.selectMemberInfo(contractRegistRequestDTO.getMemberId());
        centerId = memberDTO.getCenterId();

        Contract contract = modelMapper.map(contractRegistRequestDTO, Contract.class);

        contract.setMemberId(memberId);
        contract.setCenterId(centerId);    // 회원의 매장번호 넣기
        contract.setProductId(productId);    // 제품 번호 넣기
        contract.setCustomerId(customerId);
        contract.setStatus("WAIT");

        contract.setCustomerPhone(aesUtils.encrypt(contractRegistRequestDTO.getCustomerPhone()));
        contract.setCustomerEmail(aesUtils.encrypt(contractRegistRequestDTO.getCustomerEmail()));
        contract.setCustomerAddrress(aesUtils.encrypt(contractRegistRequestDTO.getCustomerAddrress()));
        contract.setCustomerIdentifiNo(aesUtils.encrypt(contractRegistRequestDTO.getCustomerIdentifiNo()));

        contractRepository.save(contract);
    }

    @Override
    @Transactional
    public ContractModifyDTO modifyContract(ContractModifyDTO contractModifyRequestDTO) throws GeneralSecurityException {

        String memberId = authQueryService.selectMemberIdByLoginId(contractModifyRequestDTO.getMemberId());

        // 가져온 고객 정보에 수정된 값 넣기
        CustomerModifyDTO customerModifyDTO = new CustomerModifyDTO();
        customerModifyDTO.setName(contractModifyRequestDTO.getCustomerName());
        customerModifyDTO.setAge(contractModifyRequestDTO.getCustomerAge());
        customerModifyDTO.setSex(contractModifyRequestDTO.getCustomerSex());
        customerModifyDTO.setPhone(contractModifyRequestDTO.getCustomerPhone());
        customerModifyDTO.setEmail(contractModifyRequestDTO.getCustomerEmail());
        customerModifyDTO.setCustomerId(contractModifyRequestDTO.getCustomerId());
        customerModifyDTO.setMemberId(memberId);

        customerCommandService.modifyCustomerInfo(customerModifyDTO);

        Contract contract = (Contract) contractRepository.findByContractIdAndMemberId(contractModifyRequestDTO.getContractId(), memberId)
                .orElseThrow(() -> new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND));

        // 계약서 업데이트
        Contract updateContract = modelMapper.map(contractModifyRequestDTO, Contract.class);
        updateContract.setCreatedAt(contract.getCreatedAt());
        updateContract.setUpdatedAt(contract.getUpdatedAt());
        updateContract.setActive(contract.isActive());
        updateContract.setCenterId(contract.getCenterId());
        updateContract.setCreatedUrl(contract.getCreatedUrl());
        updateContract.setCustomerSex(contract.getCustomerSex());
        updateContract.setStatus("WAIT");

        contract.setCustomerPhone(aesUtils.encrypt(contractModifyRequestDTO.getCustomerPhone()));
        contract.setCustomerEmail(aesUtils.encrypt(contractModifyRequestDTO.getCustomerEmail()));
        contract.setCustomerAddrress(aesUtils.encrypt(contractModifyRequestDTO.getCustomerAddrress()));
        contract.setCustomerIdentifiNo(aesUtils.encrypt(contractModifyRequestDTO.getCustomerIdentifiNo()));

        contractRepository.save(updateContract);

        ContractModifyDTO contractModifyDTO = modelMapper.map(updateContract, ContractModifyDTO.class);

        return contractModifyDTO;
    }

    @Override
    @Transactional
    public void deleteContract(ContractDeleteDTO contractDeleteDTO) {

        String memberId = authQueryService.selectMemberIdByLoginId(contractDeleteDTO.getMemberId());

        Contract contract = (Contract) contractRepository.findByContractIdAndMemberId(contractDeleteDTO.getContractId(), memberId)
                .orElseThrow(() -> new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND));

        contract.setActive(false);
        contract.setDeletedAt(getCurrentTime());

        contractRepository.save(contract);
    }

    @Override
    @Transactional
    public void modifyContractStatus(ContractStatusModifyDTO contractStatusModifyDTO) {

        // 관리자 ID 조회
        String adminId = authQueryService.selectMemberIdByLoginId(contractStatusModifyDTO.getAdminId());

        // 계약 조회 및 수정
        Contract contract = contractRepository.findByContractId(contractStatusModifyDTO.getContractId());

        if (contract == null) {
            throw new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND);
        }
        contract.setStatus(contractStatusModifyDTO.getStatus());
        contract.setAdminId(adminId);

        contractRepository.save(contract);

        if (contractStatusModifyDTO.getStatus().equals("APPROVED")) {
            // 판매 내역 등록
            salesHistoryCommandService.registerSalesHistory(contract.getContractId());

            // 제품 재고 수 줄이기
            ProductSelectIdDTO productSelectIdDTO = productService.selectByProductSerialNumber(contract.getSerialNum());
            String productId = productSelectIdDTO.getId();
            productCommandService.modifyProductStock(productId);
        } else if (contractStatusModifyDTO.getStatus().equals("CANCLED")) {
            salesHistoryCommandService.deleteSalesHistory(contract.getContractId());

            ProductSelectIdDTO productSelectIdDTO = productService.selectByProductSerialNumber(contract.getSerialNum());
            String productId = productSelectIdDTO.getId();
            productCommandService.deleteProductStock(productId);
        }
    }
}
