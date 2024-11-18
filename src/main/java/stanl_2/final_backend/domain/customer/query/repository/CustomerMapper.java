package stanl_2.final_backend.domain.customer.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stanl_2.final_backend.domain.customer.query.dto.CustomerDTO;

@Mapper
public interface CustomerMapper {
    CustomerDTO selectCustomerInfoById(@Param("customerId") String customerId);
}
