package stanl_2.final_backend.domain.contract.command.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.alarm.command.application.service.AlarmCommandService;
import stanl_2.final_backend.domain.contract.command.application.dto.*;
import stanl_2.final_backend.domain.contract.command.application.service.ContractCommandService;
import stanl_2.final_backend.domain.contract.command.domain.aggregate.entity.Contract;
import stanl_2.final_backend.domain.contract.command.domain.aggregate.entity.UpdateHistory;
import stanl_2.final_backend.domain.contract.command.domain.repository.ContractRepository;
import stanl_2.final_backend.domain.contract.command.domain.repository.UpdateHistoryRepository;
import stanl_2.final_backend.domain.contract.common.exception.ContractCommonException;
import stanl_2.final_backend.domain.contract.common.exception.ContractErrorCode;
import stanl_2.final_backend.domain.customer.command.application.dto.CustomerModifyDTO;
import stanl_2.final_backend.domain.customer.command.application.dto.CustomerRegistDTO;
import stanl_2.final_backend.domain.customer.command.application.service.CustomerCommandService;
import stanl_2.final_backend.domain.customer.query.dto.CustomerDTO;
import stanl_2.final_backend.domain.customer.query.service.CustomerQueryService;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.domain.product.command.application.command.service.ProductCommandService;
import stanl_2.final_backend.domain.product.query.dto.ProductSelectIdDTO;
import stanl_2.final_backend.domain.product.query.service.ProductQueryService;
import stanl_2.final_backend.domain.s3.command.application.service.S3FileService;
import stanl_2.final_backend.domain.sales_history.command.application.service.SalesHistoryCommandService;
import stanl_2.final_backend.global.utils.AESUtils;

import java.security.GeneralSecurityException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service("contractServiceImpl")
public class ContractCommandServiceImpl implements ContractCommandService {

    private final ContractRepository contractRepository;
    private final UpdateHistoryRepository updateHistoryRepository;
    private final AuthQueryService authQueryService;
    private final CustomerQueryService customerQueryService;
    private final MemberQueryService memberQueryService;
    private final CustomerCommandService customerCommandService;
    private final ProductQueryService productQueryService;
    private final ProductCommandService productCommandService;
    private final SalesHistoryCommandService salesHistoryCommandService;
    private final ModelMapper modelMapper;
    private final AESUtils aesUtils;
    private final S3FileService s3FileService;
    private final AlarmCommandService alarmCommandService;

    @Autowired
    public ContractCommandServiceImpl(ContractRepository contractRepository, UpdateHistoryRepository updateHistoryRepository,
                                      AuthQueryService authQueryService, CustomerQueryService customerQueryService,
                                      MemberQueryService memberQueryService, CustomerCommandService customerCommandService,
                                      ProductQueryService productQueryService, ProductCommandService productCommandService,
                                      SalesHistoryCommandService salesHistoryCommandService, ModelMapper modelMapper,
                                      AESUtils aesUtils, S3FileService s3FileService, AlarmCommandService alarmCommandService) {
        this.contractRepository = contractRepository;
        this.updateHistoryRepository = updateHistoryRepository;
        this.authQueryService = authQueryService;
        this.customerQueryService = customerQueryService;
        this.memberQueryService = memberQueryService;
        this.customerCommandService = customerCommandService;
        this.productQueryService = productQueryService;
        this.productCommandService = productCommandService;
        this.salesHistoryCommandService = salesHistoryCommandService;
        this.modelMapper = modelMapper;
        this.aesUtils = aesUtils;
        this.s3FileService = s3FileService;
        this.alarmCommandService = alarmCommandService;
    }

    private String  getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private String handleCustomerInfo(ContractRegistDTO contractRegistRequestDTO, String memberId) throws GeneralSecurityException {

        // 고객 정보 조회
        CustomerDTO customerDTO = customerQueryService.selectCustomerInfoByPhone(contractRegistRequestDTO.getCustomerPhone());

        if (customerDTO != null) {

            // 고객 정보 업데이트
            CustomerModifyDTO customerModifyDTO = new CustomerModifyDTO();
            customerModifyDTO.setCustomerId(customerDTO.getCustomerId());
            customerModifyDTO.setName(contractRegistRequestDTO.getCustomerName());
            customerModifyDTO.setAge(contractRegistRequestDTO.getCustomerAge());
            customerModifyDTO.setPhone(contractRegistRequestDTO.getCustomerPhone());
            customerModifyDTO.setEmail(contractRegistRequestDTO.getCustomerEmail());
            customerModifyDTO.setSex(contractRegistRequestDTO.getCustomerSex());
            customerModifyDTO.setMemberId(memberId);

            customerCommandService.modifyCustomerInfo(customerModifyDTO);

            return customerDTO.getCustomerId();
        } else if (customerDTO == null) {
            CustomerRegistDTO customerRegistDTO = new CustomerRegistDTO();
            customerRegistDTO.setName(contractRegistRequestDTO.getCustomerName());
            customerRegistDTO.setAge(contractRegistRequestDTO.getCustomerAge());
            customerRegistDTO.setPhone(contractRegistRequestDTO.getCustomerPhone());
            customerRegistDTO.setEmail(contractRegistRequestDTO.getCustomerEmail());
            if (contractRegistRequestDTO.getCustomerSex().equals("여자")) {
                customerRegistDTO.setSex("FEMALE");
            } else if (contractRegistRequestDTO.getCustomerSex().equals("남자")) {
                customerRegistDTO.setSex("MALE");
            }
            customerRegistDTO.setMemberId(memberId);

            // 고객 등록
            customerDTO = modelMapper.map(customerCommandService.registerCustomerInfo(customerRegistDTO), CustomerDTO.class);

            return customerDTO.getCustomerId();
        }
        return customerDTO.getCustomerId();
    }

    private String updateCustomerInfo(ContractModifyDTO contractModifyDTO, String memberId) throws GeneralSecurityException {
        // 고객 정보 조회
        CustomerDTO customerDTO = customerQueryService.selectCustomerInfoByPhone(contractModifyDTO.getCustomerPhone());

        if (customerDTO != null) {
            // 고객 정보 업데이트
            CustomerModifyDTO customerModifyDTO = new CustomerModifyDTO();
            customerModifyDTO.setCustomerId(customerDTO.getCustomerId());
            customerModifyDTO.setName(contractModifyDTO.getCustomerName());
            customerModifyDTO.setAge(contractModifyDTO.getCustomerAge());
            customerModifyDTO.setPhone(contractModifyDTO.getCustomerPhone());
            customerModifyDTO.setEmail(contractModifyDTO.getCustomerEmail());

            if (contractModifyDTO.getCustomerSex().equals("여자")) {
                customerModifyDTO.setSex("FEMALE");
            } else if (contractModifyDTO.getCustomerSex().equals("남자")) {
                customerModifyDTO.setSex("MALE");
            }


            customerModifyDTO.setMemberId(memberId);

            customerCommandService.modifyCustomerInfo(customerModifyDTO);

            // 업데이트된 고객 ID 반환
            return customerDTO.getCustomerId();
        }

        // 고객이 없으면 새로 등록
        CustomerRegistDTO customerRegistDTO = new CustomerRegistDTO();
        customerRegistDTO.setName(contractModifyDTO.getCustomerName());
        customerRegistDTO.setAge(contractModifyDTO.getCustomerAge());
        customerRegistDTO.setPhone(contractModifyDTO.getCustomerPhone());
        customerRegistDTO.setEmail(contractModifyDTO.getCustomerEmail());
        customerRegistDTO.setMemberId(memberId);

        // 등록 후 재조회
        customerDTO = modelMapper.map(customerCommandService.registerCustomerInfo(customerRegistDTO), CustomerDTO.class);

        if (customerDTO == null) {
            throw new ContractCommonException(ContractErrorCode.CUSTOMER_NOT_FOUND);
        }

        return customerDTO.getCustomerId();
    }

    @Override
    @Transactional
    public void registerContract(ContractRegistDTO contractRegistRequestDTO) throws GeneralSecurityException {
        String memberId = authQueryService.selectMemberIdByLoginId(contractRegistRequestDTO.getMemberId());
        String productId = productQueryService.selectByProductSerialNumber(contractRegistRequestDTO.getSerialNum()).getProductId();
        String customerId = handleCustomerInfo(contractRegistRequestDTO, memberId);
        String centerId = memberQueryService.selectMemberInfo(contractRegistRequestDTO.getMemberId()).getCenterId();

        // 계약 생성
        String customerPurchaseCondition = contractRegistRequestDTO.getCustomerPurchaseCondition();
        String customerClassifcation = contractRegistRequestDTO.getCustomerClassifcation();
        String customerSex = contractRegistRequestDTO.getCustomerSex();

        if (customerPurchaseCondition.equals("현금")) {
            contractRegistRequestDTO.setCustomerPurchaseCondition("CASH");
        } else if (customerPurchaseCondition.equals("할부")) {
            contractRegistRequestDTO.setCustomerPurchaseCondition("INSTALLMENT");
        } else if (customerPurchaseCondition.equals("리스")) {
            contractRegistRequestDTO.setCustomerPurchaseCondition("LEASE");
        }

        if (customerClassifcation.equals("개인")) {
            contractRegistRequestDTO.setCustomerClassifcation("PERSONAL");
        } else if (customerClassifcation.equals("법인")) {
            contractRegistRequestDTO.setCustomerClassifcation("BUSINESS");
        }

        Contract contract = modelMapper.map(contractRegistRequestDTO, Contract.class);
        contract.setMemberId(memberId);
        contract.setCenterId(centerId);
        contract.setProductId(productId);
        contract.setCustomerId(customerId);
        contract.setStatus("WAIT");

        // 고객 정보 암호화 후 설정
        contract.setCustomerPhone(aesUtils.encrypt(contractRegistRequestDTO.getCustomerPhone()));
        contract.setCustomerEmail(aesUtils.encrypt(contractRegistRequestDTO.getCustomerEmail()));
        contract.setCustomerAddress(aesUtils.encrypt(contractRegistRequestDTO.getCustomerAddress()));
        contract.setCustomerIdentifiNo(aesUtils.encrypt(contractRegistRequestDTO.getCustomerIdentifiNo()));

        String unescapedHtml = StringEscapeUtils.unescapeJson(contractRegistRequestDTO.getCreatedUrl());
        String updatedS3Url = s3FileService.uploadHtml(unescapedHtml, contractRegistRequestDTO.getTitle());

        contract.setCreatedUrl(updatedS3Url);

        // 계약 저장
        contractRepository.save(contract);
    }

    @Override
    @Transactional
    public void modifyContract(ContractModifyDTO contractModifyRequestDTO) throws GeneralSecurityException {
        String memberId = authQueryService.selectMemberIdByLoginId(contractModifyRequestDTO.getMemberId());
        String productId = productQueryService.selectByProductSerialNumber(contractModifyRequestDTO.getSerialNum()).getProductId();
        String customerId = updateCustomerInfo(contractModifyRequestDTO, memberId);
        String centerId = memberQueryService.selectMemberInfo(contractModifyRequestDTO.getMemberId()).getCenterId();


        // 계약 조회
        Contract contract = (Contract)contractRepository.findByContractId(contractModifyRequestDTO.getContractId());
        if (contract == null) {
            throw new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND);
        }

        // 계약 생성

        String customerPurchaseCondition = contractModifyRequestDTO.getCustomerPurchaseCondition();
        String customerClassifcation = contractModifyRequestDTO.getCustomerClassifcation();

        if (customerPurchaseCondition.equals("현금")) {
            contractModifyRequestDTO.setCustomerPurchaseCondition("CASH");
        } else if (customerPurchaseCondition.equals("할부")) {
            contractModifyRequestDTO.setCustomerPurchaseCondition("INSTALLMENT");
        } else if (customerPurchaseCondition.equals("리스")) {
            contractModifyRequestDTO.setCustomerPurchaseCondition("LEASE");
        }

        if (customerClassifcation.equals("개인")) {
            contractModifyRequestDTO.setCustomerClassifcation("PERSONAL");
        } else if (customerClassifcation.equals("법인")) {
            contractModifyRequestDTO.setCustomerClassifcation("BUSINESS");
        }

        Contract updateContract = modelMapper.map(contractModifyRequestDTO, Contract.class);
        updateContract.setMemberId(memberId);
        updateContract.setCenterId(centerId);
        updateContract.setProductId(productId);
        updateContract.setCustomerId(customerId);
        updateContract.setStatus("WAIT");

        // 고객 정보가 수정된 경우 계약서의 고객 정보도 업데이트
        updateContract.setCustomerPhone(aesUtils.encrypt(contractModifyRequestDTO.getCustomerPhone()));
        updateContract.setCustomerEmail(aesUtils.encrypt(contractModifyRequestDTO.getCustomerEmail()));
        updateContract.setCustomerAddress(aesUtils.encrypt(contractModifyRequestDTO.getCustomerAddress()));
        updateContract.setCustomerIdentifiNo(aesUtils.encrypt(contractModifyRequestDTO.getCustomerIdentifiNo()));

        // 수정된 계약 정보 저장
        contractRepository.save(updateContract);

        // 수정 이력 저장
        String unescapedHtml = StringEscapeUtils.unescapeJson(contractModifyRequestDTO.getCreatedUrl());
        String updatedS3Url = s3FileService.uploadHtml(unescapedHtml, contractModifyRequestDTO.getTitle());

        UpdateHistoryRegistDTO updateHistoryRegistDTO = new UpdateHistoryRegistDTO();
        updateHistoryRegistDTO.setContent(updatedS3Url);
        updateHistoryRegistDTO.setMemberId(memberId);
        updateHistoryRegistDTO.setContractId(contractModifyRequestDTO.getContractId());

        updateHistoryRepository.save(modelMapper.map(updateHistoryRegistDTO, UpdateHistory.class));
    }

    @Override
    @Transactional
    public void deleteContract(ContractDeleteDTO contractDeleteDTO) {

        Contract contract = (Contract) contractRepository.findByContractId(contractDeleteDTO.getContractId());

        if (contract == null) {
            throw new ContractCommonException(ContractErrorCode.CONTRACT_NOT_FOUND);
        }

        contract.setActive(false);
        contract.setDeletedAt(getCurrentTime());

        contractRepository.save(contract);
    }

    @Override
    @Transactional
    public void modifyContractStatus(ContractStatusModifyDTO contractStatusModifyDTO) throws GeneralSecurityException {

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

        ContractAlarmDTO contractAlarmDTO = new ContractAlarmDTO(contract.getContractId(), contract.getCustomerName(),
                contract.getMemberId(), contract.getAdminId());

        alarmCommandService.sendContractAlarm(contractAlarmDTO);

        if (contractStatusModifyDTO.getStatus().equals("APPROVED")) {
            // 판매 내역 등록
            salesHistoryCommandService.registerSalesHistory(contract.getContractId());

            // 제품 재고 수 줄이기
            ProductSelectIdDTO productSelectIdDTO = productQueryService.selectByProductSerialNumber(contract.getSerialNum());
            String productId = productSelectIdDTO.getProductId();
            productCommandService.modifyProductStock(productId, contract.getNumberOfVehicles());
        } else if (contractStatusModifyDTO.getStatus().equals("CANCLED")) {
            salesHistoryCommandService.deleteSalesHistory(contract.getContractId());

            ProductSelectIdDTO productSelectIdDTO = productQueryService.selectByProductSerialNumber(contract.getSerialNum());
            String productId = productSelectIdDTO.getProductId();
            productCommandService.deleteProductStock(productId, contract.getNumberOfVehicles());
        }
    }
}
