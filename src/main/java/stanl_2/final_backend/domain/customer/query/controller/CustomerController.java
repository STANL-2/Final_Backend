package stanl_2.final_backend.domain.customer.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.customer.query.service.CustomerQueryService;

@RestController(value = "queryCustomerController")
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerQueryService customerQueryService;

    @Autowired
    public CustomerController(CustomerQueryService customerQueryService) {
        this.customerQueryService = customerQueryService;
    }
}
