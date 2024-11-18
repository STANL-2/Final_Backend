package stanl_2.final_backend.domain.customer.query.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stanl_2.final_backend.domain.customer.query.dto.CustomerDTO;
import stanl_2.final_backend.domain.customer.query.dto.CustomerNameListDTO;

import java.security.GeneralSecurityException;
import java.util.List;

public interface CustomerQueryService {
    CustomerDTO selectCustomerInfo(String customerId) throws GeneralSecurityException;

    Page<CustomerDTO> selectCustomerList(Pageable pageable);
}
