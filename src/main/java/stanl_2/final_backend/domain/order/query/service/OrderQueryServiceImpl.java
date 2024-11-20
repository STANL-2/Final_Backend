package stanl_2.final_backend.domain.order.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
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
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderMapper orderMapper;
    private final AuthQueryService authQueryService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public OrderQueryServiceImpl(OrderMapper orderMapper, AuthQueryService authQueryService, RedisTemplate redisTemplate) {
        this.orderMapper = orderMapper;
        this.authQueryService = authQueryService;
        this.redisTemplate = redisTemplate;
    }

    // 영업사원 조회
    @Override
    @Transactional(readOnly = true)
    public Page<OrderSelectAllDTO> selectAllEmployee(String loginId, Pageable pageable) {
        String memberId = authQueryService.selectMemberIdByLoginId(loginId);

        int offset = Math.toIntExact(pageable.getOffset());
        int pageSize = pageable.getPageSize();

        String cacheKey = "myCache::orders::offset=" + offset + "::size=" + pageSize;

        // 캐시 조회
        List<OrderSelectAllDTO> orders = (List<OrderSelectAllDTO>) redisTemplate.opsForValue().get(cacheKey);

        if (orders == null) {
            System.out.println("데이터베이스에서 데이터 조회 중...");
            orders = orderMapper.findAllOrderByMemberId(offset, pageSize, memberId);

            // 결과가 null이거나 빈 리스트인지 확인
            if (orders == null || orders.isEmpty()) {
                throw new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND);
            }

            // 캐시에 데이터 저장 (빈 리스트는 저장하지 않음)
            redisTemplate.opsForValue().set(cacheKey, orders);
        } else {
            System.out.println("캐시에서 데이터 조회 중...");
        }

        // 전체 개수 조회
        int totalElements = orderMapper.findOrderCountByMemberId(memberId);

        return new PageImpl<>(orders, pageable, totalElements);
    }


    @Override
    @Transactional(readOnly = true)
    public OrderSelectIdDTO selectDetailOrderEmployee(OrderSelectIdDTO orderSelectIdDTO) {
        String memberId = authQueryService.selectMemberIdByLoginId(orderSelectIdDTO.getMemberId());

        OrderSelectIdDTO order = orderMapper.findOrderByIdAndMemberId(orderSelectIdDTO.getOrderId(), memberId);

        if (order == null) {
            throw new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND);
        }

        return order;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderSelectSearchDTO> selectSearchOrdersEmployee(OrderSelectSearchDTO orderSelectSearchDTO, Pageable pageable) {

        String memberId = authQueryService.selectMemberIdByLoginId(orderSelectSearchDTO.getMemberId());
        orderSelectSearchDTO.setMemberId(memberId);

        int offset = Math.toIntExact(pageable.getOffset());
        int pageSize = pageable.getPageSize();
        List<OrderSelectSearchDTO> orders = orderMapper.findSearchOrderByMemberId(offset, pageSize, orderSelectSearchDTO);

        if (orders == null || orders.isEmpty()) {
            throw new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND);
        }

        Integer count = orderMapper.findOrderSearchCountByMemberId(orderSelectSearchDTO);
        int totalOrder = (count != null) ? count : 0;

        return new PageImpl<>(orders, pageable, totalOrder);
    }

    // 영업담당자, 영업관리자 조회
    @Override
    @Transactional(readOnly = true)
    public Page<OrderSelectAllDTO> selectAll(Pageable pageable) {

        int offset = Math.toIntExact(pageable.getOffset());
        int pageSize = pageable.getPageSize();

        String cacheKey = "myCache::orders::offset=" + offset + "::size=" + pageSize;

        // 캐시 조회
        List<OrderSelectAllDTO> orders = (List<OrderSelectAllDTO>) redisTemplate.opsForValue().get(cacheKey);

        if (orders == null) {
            System.out.println("데이터베이스에서 데이터 조회 중...");
            orders = orderMapper.findAllOrder(offset, pageSize);

            // 결과가 null이거나 빈 리스트인지 확인
            if (orders == null || orders.isEmpty()) {
                throw new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND);
            }

            // 캐시에 데이터 저장 (빈 리스트는 저장하지 않음)
            redisTemplate.opsForValue().set(cacheKey, orders);
        } else {
            System.out.println("캐시에서 데이터 조회 중...");
        }

        // 전체 개수 조회
        Integer count = orderMapper.findOrderCount();
        int totalOrder = (count != null) ? count : 0;

        return new PageImpl<>(orders, pageable, totalOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderSelectIdDTO selectDetailOrder(OrderSelectIdDTO orderSelectIdDTO) {

        OrderSelectIdDTO order = orderMapper.findOrderByOrderId(orderSelectIdDTO.getOrderId());

        if (order == null) {
            throw new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND);
        }

        return order;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderSelectSearchDTO> selectSearchOrders(OrderSelectSearchDTO orderSelectSearchDTO, Pageable pageable) {

        int offset = Math.toIntExact(pageable.getOffset());
        int pageSize = pageable.getPageSize();
        List<OrderSelectSearchDTO> orders = orderMapper.findSearchOrder(offset, pageSize, orderSelectSearchDTO);

        if (orders == null || orders.isEmpty()) {
            throw new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND);
        }

        Integer count = orderMapper.findOrderSearchCount(orderSelectSearchDTO);
        int totalOrder = (count != null) ? count : 0;

        return new PageImpl<>(orders, pageable, totalOrder);
    }
}
