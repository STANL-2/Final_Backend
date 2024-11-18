package stanl_2.final_backend.domain.customer.query.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.customer.query.repository.CustomerMapper;

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
}
