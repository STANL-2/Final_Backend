package stanl_2.final_backend.domain.customer.query.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.customer.common.exception.CustomerCommonException;
import stanl_2.final_backend.domain.customer.common.exception.CustomerErrorCode;
import stanl_2.final_backend.domain.customer.query.dto.CustomerContractDTO;
import stanl_2.final_backend.domain.customer.query.dto.CustomerDTO;
import stanl_2.final_backend.domain.customer.query.dto.CustomerExcelDTO;
import stanl_2.final_backend.domain.customer.query.dto.CustomerSearchDTO;
import stanl_2.final_backend.domain.customer.query.repository.CustomerMapper;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.global.excel.ExcelUtilsV1;
import stanl_2.final_backend.global.utils.AESUtils;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("queryCustomerService")
public class CustomerQueryServiceImpl implements CustomerQueryService{

    private final CustomerMapper customerMapper;
    private final AESUtils aesUtils;
    private final MemberQueryService memberQueryService;
    private final ExcelUtilsV1 excelUtilsV1;

    @Autowired
    public CustomerQueryServiceImpl(CustomerMapper customerMapper,
                                    AESUtils aesUtils,
                                    MemberQueryService memberQueryService, ExcelUtilsV1 excelUtilsV1) {
        this.customerMapper = customerMapper;
        this.aesUtils = aesUtils;
        this.memberQueryService = memberQueryService;
        this.excelUtilsV1 = excelUtilsV1;
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDTO selectCustomerInfo(String customerId) throws GeneralSecurityException {

        CustomerDTO customerInfoDTO = customerMapper.selectCustomerInfoById(customerId);

        customerInfoDTO.setPhone(aesUtils.decrypt(customerInfoDTO.getPhone()));
        customerInfoDTO.setEmergePhone(aesUtils.decrypt(customerInfoDTO.getEmergePhone()));
        customerInfoDTO.setEmail(aesUtils.decrypt(customerInfoDTO.getEmail()));

        return customerInfoDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerDTO> selectCustomerList(Pageable pageable) throws GeneralSecurityException {
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        List<CustomerDTO> customerList = customerMapper.selectCustomerList(page*size, size);
        int totalElements = customerMapper.selectCustomerCount();

        // 복호화
        for(int i=0;i< customerList.size();i++){
            customerList.get(i).setPhone(aesUtils.decrypt(customerList.get(i).getPhone()));
            customerList.get(i).setEmergePhone(aesUtils.decrypt(customerList.get(i).getEmergePhone()));
            customerList.get(i).setEmail(aesUtils.decrypt(customerList.get(i).getEmail()));
        }


        return new PageImpl<>(customerList, pageable, totalElements);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerSearchDTO> findCustomerByCondition(Pageable pageable, CustomerSearchDTO customerSearchDTO) throws GeneralSecurityException {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        // 정렬 정보 가져오기
        Sort sort = pageable.getSort();
        String sortField = null;
        String sortOrder = null;
        if (sort.isSorted()) {
            sortField = sort.iterator().next().getProperty();
            sortOrder = sort.iterator().next().isAscending() ? "ASC" : "DESC";
        }

        Map<String, Object> params = new HashMap<>();
        params.put("offset", offset);
        params.put("size", size);
        params.put("customerId", customerSearchDTO.getCustomerId());
        params.put("name", customerSearchDTO.getName());
        params.put("sex", customerSearchDTO.getSex());
        params.put("phone", aesUtils.encrypt(customerSearchDTO.getPhone()));
        params.put("memberId", customerSearchDTO.getMemberId());

        params.put("sortField", sortField);
        params.put("sortOrder", sortOrder);

        List<CustomerSearchDTO> customerList = customerMapper.findCustomerByConditions(params);

        Integer count = customerMapper.findCustomerCnt(params);

        for(int i=0;i< customerList.size();i++){
            customerList.get(i).setPhone(aesUtils.decrypt(customerList.get(i).getPhone()));
            // 이름으로 변환
            customerList.get(i).setMemberId(memberQueryService.selectNameById(customerList.get(i).getMemberId()));
        }

        return new PageImpl<>(customerList, pageable, count);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDTO selectCustomerInfoByPhone(String customerPhone) throws GeneralSecurityException {

        String encryptedPhone = aesUtils.encrypt(customerPhone);

        CustomerDTO customerInfoDTO = customerMapper.selectCustomerInfoByPhone(encryptedPhone);

        if (customerInfoDTO == null) {
            return null;
        }

        customerInfoDTO.setPhone(aesUtils.decrypt(customerInfoDTO.getPhone()));
        customerInfoDTO.setEmergePhone(aesUtils.decrypt(customerInfoDTO.getEmergePhone()));
        customerInfoDTO.setEmail(aesUtils.decrypt(customerInfoDTO.getEmail()));

        return customerInfoDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public String selectCustomerNameById(String customerId) throws GeneralSecurityException {

        CustomerDTO customerInfoDTO = customerMapper.selectCustomerInfoById(customerId);

//        String customerName = aesUtils.decrypt(customerInfoDTO.getName());

//        return customerName;
        return customerInfoDTO.getName();
    }

    @Override
    public List<String> selectCustomerId(String customerName) throws GeneralSecurityException {
        List<CustomerDTO> customerInfoDTO = customerMapper.findCustomerInfoByName(customerName);

        List<String> customerIds = new ArrayList<>();

        customerInfoDTO.forEach(customerInfo -> {
            customerIds.add(customerInfo.getCustomerId());
        });

        return customerIds;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerContractDTO> selectCustomerContractInfo(String customerId, Pageable pageable) {

        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();

        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("offset", page*size);
        params.put("size", size);

        List<CustomerContractDTO> customerContractDTOList = customerMapper.findCustomerContractById(params);
        
        int totalElements = customerMapper.selectCustomerContractCnt(customerId);

        return new PageImpl<>(customerContractDTOList, pageable, totalElements);
    }

    @Override
    public void exportCustomerToExcel(HttpServletResponse response) {
        List<CustomerExcelDTO> customerExcels = customerMapper.findCustomerForExcel();


        if(customerExcels == null) {
            throw new CustomerCommonException(CustomerErrorCode.CUSTOMER_NOT_FOUND);
        }

        excelUtilsV1.download(CustomerExcelDTO.class, customerExcels, "customerExcel", response);
    }
}
