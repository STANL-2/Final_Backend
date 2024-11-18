package stanl_2.final_backend.domain.customer.command.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.customer.command.application.dto.CustomerRegistDTO;
import stanl_2.final_backend.domain.customer.command.application.service.CustomerCommandService;
import stanl_2.final_backend.domain.customer.command.domain.aggregate.entity.Customer;
import stanl_2.final_backend.domain.customer.command.domain.repository.CustomerRepository;

@Slf4j
@Service("commandCustomerService")
public class CustomerCommandServiceImpl implements CustomerCommandService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerCommandServiceImpl(CustomerRepository customerRepository,
                                      ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void registerCustomerInfo(CustomerRegistDTO customerRegistDTO) {

        Customer customer = modelMapper.map(customerRegistDTO, Customer.class);

        log.info(customer.getMemberId());
        customerRepository.save(customer);
    }
}
