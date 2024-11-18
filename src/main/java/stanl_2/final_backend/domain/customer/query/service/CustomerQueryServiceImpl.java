package stanl_2.final_backend.domain.customer.query.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.customer.query.dto.CustomerDTO;
import stanl_2.final_backend.domain.customer.query.repository.CustomerMapper;
import stanl_2.final_backend.global.utils.AESUtils;

import java.security.GeneralSecurityException;

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
}
