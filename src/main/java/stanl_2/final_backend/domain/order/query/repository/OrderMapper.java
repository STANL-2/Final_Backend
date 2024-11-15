package stanl_2.final_backend.domain.order.query.repository;

import org.apache.ibatis.annotations.Mapper;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectAllDTO;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectIdDTO;

import java.util.List;

@Mapper
public interface OrderMapper {
    List<OrderSelectAllDTO> findAllOrderByMemberId(int offset, int pageSize, String memberId);

    int findOrderCountByMemberId(String memberId);

    OrderSelectIdDTO findOrderByIdAndMemberId(String orderId, String memberId);
}
