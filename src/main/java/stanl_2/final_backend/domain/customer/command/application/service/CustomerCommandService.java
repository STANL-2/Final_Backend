package stanl_2.final_backend.domain.customer.command.application.service;

import stanl_2.final_backend.domain.customer.command.application.dto.CustomerModifyDTO;
import stanl_2.final_backend.domain.customer.command.application.dto.CustomerRegistDTO;
import stanl_2.final_backend.domain.customer.command.application.dto.CustomerResponseDTO;
import stanl_2.final_backend.domain.customer.command.domain.aggregate.entity.Customer;

import java.security.GeneralSecurityException;

public interface CustomerCommandService {
    CustomerResponseDTO registerCustomerInfo(CustomerRegistDTO customerRegistDTO) throws GeneralSecurityException;

    void modifyCustomerInfo(CustomerModifyDTO customerModifyDTO) throws GeneralSecurityException;

    void deleteCustomerId(String customerId);
}
