package stanl_2.final_backend.domain.customer.command.application.service;

import stanl_2.final_backend.domain.customer.command.application.dto.CustomerModifyDTO;
import stanl_2.final_backend.domain.customer.command.application.dto.CustomerRegistDTO;

public interface CustomerCommandService {
    void registerCustomerInfo(CustomerRegistDTO customerRegistDTO);

    void modifyCustomerId(CustomerModifyDTO customerModifyDTO);
}
