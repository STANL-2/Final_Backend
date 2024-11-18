package stanl_2.final_backend.domain.customer.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stanl_2.final_backend.domain.customer.query.dto.CustomerDTO;

import java.util.List;

@Mapper
public interface CustomerMapper {
    CustomerDTO selectCustomerInfoById(@Param("customerId") String customerId);

    List<CustomerDTO> selectCustomerList(int offset, int size);

    int selectCustomerCount();
}
