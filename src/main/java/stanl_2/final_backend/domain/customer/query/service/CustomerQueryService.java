package stanl_2.final_backend.domain.customer.query.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stanl_2.final_backend.domain.customer.query.dto.CustomerContractDTO;
import stanl_2.final_backend.domain.customer.query.dto.CustomerDTO;
import stanl_2.final_backend.domain.customer.query.dto.CustomerSearchDTO;

import java.security.GeneralSecurityException;
import java.util.List;

public interface CustomerQueryService {
    CustomerDTO selectCustomerInfo(String customerId) throws GeneralSecurityException;

    List<String> selectCustomerId(String customerName) throws GeneralSecurityException;

    Page<CustomerDTO> selectCustomerList(Pageable pageable) throws GeneralSecurityException;

    Page<CustomerSearchDTO> findCustomerByCondition(Pageable pageable, CustomerSearchDTO customerSearchDTO) throws GeneralSecurityException;

    CustomerDTO selectCustomerInfoByPhone(String customerPhone) throws GeneralSecurityException;

    String selectCustomerNameById(String customerId) throws GeneralSecurityException;

    Page<CustomerContractDTO> selectCustomerContractInfo(String customerId, Pageable pageable);

    void exportCustomerToExcel(HttpServletResponse response);
}
