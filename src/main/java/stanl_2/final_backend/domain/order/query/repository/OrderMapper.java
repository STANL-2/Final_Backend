package stanl_2.final_backend.domain.order.query.repository;

import org.apache.ibatis.annotations.Mapper;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectAllDTO;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectIdDTO;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectSearchDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    List<OrderSelectSearchDTO> findSearchOrderByMemberId(int offset, int pageSize, OrderSelectSearchDTO orderSelectSearchDTO);

    int findOrderCountByMemberId(String memberId);

    OrderSelectIdDTO findOrderByIdAndMemberId(String orderId, String memberId);

    List<OrderSelectAllDTO> findAllOrderByMemberId(int offset, int pageSize, String memberId);

    int findOrderSearchCountByMemberId(Map<String, Object> map);

    List<OrderSelectSearchDTO> findSearchOrderByMemberId(Map<String, Object> map);

    List<OrderSelectAllDTO> findAllOrder(int offset, int pageSize);

    int findOrderCount();

    OrderSelectIdDTO findOrderByOrderId(String orderId);

    List<OrderSelectSearchDTO> findSearchOrder(Map<String, Object> map);

    int findOrderSearchCount(Map<String, Object> map);
}
