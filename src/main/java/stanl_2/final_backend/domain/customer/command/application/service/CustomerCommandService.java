package stanl_2.final_backend.domain.customer.command.application.service;

import stanl_2.final_backend.domain.customer.command.application.dto.CustomerModifyDTO;
import stanl_2.final_backend.domain.customer.command.application.dto.CustomerRegistDTO;

import java.security.GeneralSecurityException;

public interface CustomerCommandService {
    void registerCustomerInfo(CustomerRegistDTO customerRegistDTO) throws GeneralSecurityException;

    void modifyCustomerInfo(CustomerModifyDTO customerModifyDTO) throws GeneralSecurityException;

    void deleteCustomerId(String customerId);
}
