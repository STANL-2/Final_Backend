package stanl_2.final_backend.domain.customer.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stanl_2.final_backend.domain.customer.query.dto.CustomerDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface CustomerMapper {
    CustomerDTO selectCustomerInfoById(@Param("customerId") String customerId);

    List<CustomerDTO> selectCustomerList(@Param("offset") int offset, @Param("size") int size);

    int selectCustomerCount();

    List<CustomerDTO> findCustomerByConditions(Map<String, Object> map);

    int findCustomerCnt(Map<String, Object> map);

}
