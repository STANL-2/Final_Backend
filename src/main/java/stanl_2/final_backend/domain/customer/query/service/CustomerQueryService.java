package stanl_2.final_backend.domain.customer.query.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stanl_2.final_backend.domain.customer.query.dto.CustomerDTO;
import stanl_2.final_backend.domain.customer.query.dto.CustomerSearchDTO;

import java.security.GeneralSecurityException;

public interface CustomerQueryService {
    CustomerDTO selectCustomerInfo(String customerId) throws GeneralSecurityException;

    Page<CustomerDTO> selectCustomerList(Pageable pageable) throws GeneralSecurityException;

    Page<CustomerDTO> findCustomerByCondition(Pageable pageable, CustomerSearchDTO customerSearchDTO) throws GeneralSecurityException;

    CustomerDTO selectCustomerInfoByPhone(String customerPhone) throws GeneralSecurityException;
}
