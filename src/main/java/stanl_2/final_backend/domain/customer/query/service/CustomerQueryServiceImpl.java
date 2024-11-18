package stanl_2.final_backend.domain.customer.query.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.customer.query.dto.CustomerDTO;
import stanl_2.final_backend.domain.customer.query.repository.CustomerMapper;

@Slf4j
@Service("queryCustomerService")
public class CustomerQueryServiceImpl implements CustomerQueryService{

    private final CustomerMapper customerMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerQueryServiceImpl(CustomerMapper customerMapper,
                                    ModelMapper modelMapper) {
        this.customerMapper = customerMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public CustomerDTO selectCustomerInfo(String customerId) {

        CustomerDTO customerInfoDTO = customerMapper.selectCustomerInfoById(customerId);

        return customerInfoDTO;
    }
}
