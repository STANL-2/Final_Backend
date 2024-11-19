package stanl_2.final_backend.domain.order.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.order.common.exception.OrderCommonException;
import stanl_2.final_backend.domain.order.common.exception.OrderErrorCode;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectAllDTO;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectIdDTO;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectSearchDTO;
import stanl_2.final_backend.domain.order.query.repository.OrderMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderMapper orderMapper;

    @Autowired
    public OrderQueryServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    public Page<OrderSelectAllDTO> selectAll(String memberId, Pageable pageable) {
        int offset = Math.toIntExact(pageable.getOffset());
        int pageSize = pageable.getPageSize();
        List<OrderSelectAllDTO> orders = orderMapper.findAllOrderByMemberId(offset, pageSize, memberId);

        if (orders == null || orders.isEmpty()) {
            throw new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND);
        }

        int totalElements = orderMapper.findOrderCountByMemberId(memberId);

        return new PageImpl<>(orders, pageable, totalElements);
    }

    @Override
    public OrderSelectIdDTO selectDetailOrder(OrderSelectIdDTO orderSelectIdDTO) {

        OrderSelectIdDTO order = orderMapper.findOrderByIdAndMemberId(orderSelectIdDTO.getOrderId(), orderSelectIdDTO.getMemberId());

        if (order == null) {
            throw new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND);
        }

        return order;
    }

    @Override
    public Page<OrderSelectSearchDTO> selectSearchOrders(OrderSelectSearchDTO orderSelectSearchDTO, Pageable pageable) {

        Map<String, Object> map = new HashMap<>();
        map.put("memId", orderSelectSearchDTO.getMemId());
        map.put("title", orderSelectSearchDTO.getTitle());
        map.put("status", orderSelectSearchDTO.getStatus());
        map.put("adminId", orderSelectSearchDTO.getAdminId());
        map.put("memberId", orderSelectSearchDTO.getMemberId());
        map.put("startDate", orderSelectSearchDTO.getStartDate());
        map.put("endDate", orderSelectSearchDTO.getEndDate());
        map.put("pageSize", pageable.getPageSize());
        map.put("offset", pageable.getOffset());

        List<OrderSelectSearchDTO> orders = orderMapper.findSearchOrderByMemberId(map);

        if (orders == null || orders.isEmpty()) {
            throw new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND);
        }

        int totalElements = orderMapper.findOrderSearchCountByMemberId(map);

        return new PageImpl<>(orders, pageable, totalElements);
    }
}
