package stanl_2.final_backend.domain.order.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectAllDTO;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectIdDTO;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectSearchDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    List<OrderSelectSearchDTO> findSearchOrderByMemberId(@Param("offset") int offset,
                                                         @Param("pageSize") int pageSize,
                                                         @Param("orderSelectSearchDTO") OrderSelectSearchDTO orderSelectSearchDTO);

    int findOrderCountByMemberId(String memberId);

    OrderSelectIdDTO findOrderByIdAndMemberId(String orderId, String memberId);

    List<OrderSelectAllDTO> findAllOrderByMemberId(int offset, int pageSize, String memberId);

    int findOrderSearchCountByMemberId(OrderSelectSearchDTO orderSelectSearchDTO);

    List<OrderSelectSearchDTO> findSearchOrderByMemberId(Map<String, Object> map);

    List<OrderSelectAllDTO> findAllOrder(int offset, int pageSize);

    int findOrderCount();

    OrderSelectIdDTO findOrderByOrderId(String orderId);

    List<OrderSelectSearchDTO> findSearchOrder(@Param("offset") int offset,
                                               @Param("pageSize") int pageSize,
                                               @Param("orderSelectSearchDTO") OrderSelectSearchDTO orderSelectSearchDTO);

    int findOrderSearchCount(OrderSelectSearchDTO orderSelectSearchDTO);
}
