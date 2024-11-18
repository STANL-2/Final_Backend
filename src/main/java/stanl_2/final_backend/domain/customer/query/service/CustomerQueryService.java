package stanl_2.final_backend.domain.customer.query.service;

import stanl_2.final_backend.domain.customer.query.dto.CustomerDTO;

import java.security.GeneralSecurityException;

public interface CustomerQueryService {
    CustomerDTO selectCustomerInfo(String customerId) throws GeneralSecurityException;
}
