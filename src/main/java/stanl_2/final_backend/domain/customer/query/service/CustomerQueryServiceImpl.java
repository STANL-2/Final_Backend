package stanl_2.final_backend.domain.customer.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.customer.query.dto.CustomerDTO;
import stanl_2.final_backend.domain.customer.query.repository.CustomerMapper;
import stanl_2.final_backend.global.utils.AESUtils;

import java.security.GeneralSecurityException;
import java.util.List;

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
    @Transactional
    public CustomerDTO selectCustomerInfo(String customerId) throws GeneralSecurityException {

        CustomerDTO customerInfoDTO = customerMapper.selectCustomerInfoById(customerId);

        customerInfoDTO.setPhone(aesUtils.decrypt(customerInfoDTO.getPhone()));
        customerInfoDTO.setEmergePhone(aesUtils.decrypt(customerInfoDTO.getEmergePhone()));
        customerInfoDTO.setEmail(aesUtils.decrypt(customerInfoDTO.getEmail()));

        return customerInfoDTO;
    }

    @Override
    @Transactional
    public Page<CustomerDTO> selectCustomerList(Pageable pageable) {
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        List<CustomerDTO> customerList = customerMapper.selectCustomerList(page*size, size);
        int totalElements = customerMapper.selectCustomerCount();

        return new PageImpl<>(customerList, pageable, totalElements);
    }
}
