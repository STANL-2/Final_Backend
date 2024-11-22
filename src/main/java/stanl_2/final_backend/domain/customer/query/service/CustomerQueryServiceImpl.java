package stanl_2.final_backend.domain.customer.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.customer.query.dto.CustomerDTO;
import stanl_2.final_backend.domain.customer.query.dto.CustomerSearchDTO;
import stanl_2.final_backend.domain.customer.query.repository.CustomerMapper;
import stanl_2.final_backend.global.utils.AESUtils;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("queryCustomerService")
public class CustomerQueryServiceImpl implements CustomerQueryService{

    private final CustomerMapper customerMapper;
    private final AESUtils aesUtils;

    @Autowired
    public CustomerQueryServiceImpl(CustomerMapper customerMapper,
                                    AESUtils aesUtils) {
        this.customerMapper = customerMapper;
        this.aesUtils = aesUtils;
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
    public Page<CustomerDTO> findCustomerByCondition(Pageable pageable, CustomerSearchDTO customerSearchDTO) throws GeneralSecurityException {
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int offset = page*size;

        Map<String, Object> params = new HashMap<>();
        params.put("offset", offset);
        params.put("size", size);
        params.put("name", customerSearchDTO.getName());
        params.put("sex", customerSearchDTO.getSex());
        params.put("phone", customerSearchDTO.getPhone());

        List<CustomerDTO> customerList = customerMapper.findCustomerByConditions(params);

        Integer count = customerMapper.findCustomerCnt(params);

        for(int i=0;i< customerList.size();i++){
            customerList.get(i).setPhone(aesUtils.decrypt(customerList.get(i).getPhone()));
            customerList.get(i).setEmergePhone(aesUtils.decrypt(customerList.get(i).getEmergePhone()));
            customerList.get(i).setEmail(aesUtils.decrypt(customerList.get(i).getEmail()));
        }


        return new PageImpl<>(customerList, pageable, count);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDTO selectCustomerInfoByPhone(String customerPhone) throws GeneralSecurityException {

        String encryptedPhone = aesUtils.encrypt(customerPhone);

        CustomerDTO customerInfoDTO = customerMapper.selectCustomerInfoByPhone(encryptedPhone);

        if (customerInfoDTO == null) {
            return customerInfoDTO;
        }

        customerInfoDTO.setPhone(aesUtils.decrypt(customerInfoDTO.getPhone()));
        customerInfoDTO.setEmergePhone(aesUtils.decrypt(customerInfoDTO.getEmergePhone()));
        customerInfoDTO.setEmail(aesUtils.decrypt(customerInfoDTO.getEmail()));

        return customerInfoDTO;
    }
}
