package stanl_2.final_backend.domain.customer.command.domain.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.customer.command.application.dto.CustomerModifyDTO;
import stanl_2.final_backend.domain.customer.command.application.dto.CustomerRegistDTO;
import stanl_2.final_backend.domain.customer.command.application.dto.CustomerResponseDTO;
import stanl_2.final_backend.domain.customer.command.application.service.CustomerCommandService;
import stanl_2.final_backend.domain.customer.command.domain.aggregate.entity.Customer;
import stanl_2.final_backend.domain.customer.command.domain.repository.CustomerRepository;
import stanl_2.final_backend.domain.customer.common.exception.CustomerCommonException;
import stanl_2.final_backend.domain.customer.common.exception.CustomerErrorCode;
import stanl_2.final_backend.global.utils.AESUtils;

import java.security.GeneralSecurityException;

@Slf4j
@Service("commandCustomerService")
public class CustomerCommandServiceImpl implements CustomerCommandService {

    @PersistenceContext
    private EntityManager em;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final AESUtils aesUtils;

    @Autowired
    public CustomerCommandServiceImpl(CustomerRepository customerRepository,
                                      ModelMapper modelMapper,
                                      AESUtils aesUtils) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.aesUtils = aesUtils;
    }

    @Override
    @Transactional
    public CustomerResponseDTO registerCustomerInfo(CustomerRegistDTO customerRegistDTO) throws GeneralSecurityException {

        Customer customer = modelMapper.map(customerRegistDTO, Customer.class);

        customer.setPhone(aesUtils.encrypt(customer.getPhone()));
        customer.setEmergePhone(aesUtils.encrypt(customer.getEmergePhone()));
        customer.setEmail(aesUtils.encrypt(customer.getEmail()));

        return modelMapper.map(customerRepository.saveAndFlush(customer), CustomerResponseDTO.class);
    }

    @Override
    @Transactional
    public void modifyCustomerInfo(CustomerModifyDTO customerModifyDTO) throws GeneralSecurityException {

        Customer customer = customerRepository.findById(customerModifyDTO.getCustomerId())
                .orElseThrow(() -> new CustomerCommonException(CustomerErrorCode.CUSTOMER_NOT_FOUND));

        customerModifyDTO.setPhone(aesUtils.encrypt(customerModifyDTO.getPhone()));
        customerModifyDTO.setEmergePhone(aesUtils.encrypt(customerModifyDTO.getEmergePhone()));
        customerModifyDTO.setEmail(aesUtils.encrypt(customerModifyDTO.getEmail()));

        modelMapper.map(customerModifyDTO, customer);

        customerRepository.save(customer);
    }

    @Override
    @Transactional
    public void deleteCustomerId(String customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerCommonException(CustomerErrorCode.CUSTOMER_NOT_FOUND));

        customer.setActive(false);

        customerRepository.save(customer);
    }
}