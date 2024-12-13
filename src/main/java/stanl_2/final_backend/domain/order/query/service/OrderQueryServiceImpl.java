package stanl_2.final_backend.domain.order.query.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.domain.order.common.exception.OrderCommonException;
import stanl_2.final_backend.domain.order.common.exception.OrderErrorCode;
import stanl_2.final_backend.domain.order.query.dto.OrderExcelDTO;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectAllDTO;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectIdDTO;
import stanl_2.final_backend.domain.order.query.dto.OrderSelectSearchDTO;
import stanl_2.final_backend.domain.order.query.repository.OrderMapper;
import stanl_2.final_backend.global.excel.ExcelUtilsV1;

import java.security.GeneralSecurityException;
import java.util.List;

@Slf4j
@Service
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderMapper orderMapper;
    private final AuthQueryService authQueryService;
    private final MemberQueryService memberQueryService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ExcelUtilsV1 excelUtilsV1;

    @Autowired
    public OrderQueryServiceImpl(OrderMapper orderMapper, AuthQueryService authQueryService, MemberQueryService memberQueryService, RedisTemplate redisTemplate, ExcelUtilsV1 excelUtilsV1) {
        this.orderMapper = orderMapper;
        this.authQueryService = authQueryService;
        this.memberQueryService = memberQueryService;
        this.redisTemplate = redisTemplate;
        this.excelUtilsV1 = excelUtilsV1;
    }

    // 영업사원 조회
    @Override
    @Transactional(readOnly = true)
    public Page<OrderSelectAllDTO> selectAllEmployee(String loginId, Pageable pageable) {
        String memberId = authQueryService.selectMemberIdByLoginId(loginId);

        int offset = Math.toIntExact(pageable.getOffset());
        int pageSize = pageable.getPageSize();

        // 정렬 정보 가져오기
        Sort sort = pageable.getSort();
        String sortField = null;
        String sortOrder = null;
        if (sort.isSorted()) {
            sortField = sort.iterator().next().getProperty();
            sortOrder = sort.iterator().next().isAscending() ? "ASC" : "DESC";
        }

        List<OrderSelectAllDTO> orders = orderMapper.findAllOrderByMemberId(offset, pageSize, memberId, sortField, sortOrder);

        // 결과가 null이거나 빈 리스트인지 확인
        if (orders == null || orders.isEmpty()) {
            throw new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND);
        }

        // 전체 개수 조회
        Integer count = orderMapper.findOrderCountByMemberId(memberId);
        int totalOrder = (count != null) ? count : 0;

        return new PageImpl<>(orders, pageable, totalOrder);
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
    public Page<OrderSelectSearchDTO> selectSearchOrdersEmployee(OrderSelectSearchDTO orderSelectSearchDTO, Pageable pageable) throws GeneralSecurityException {

        String memberId = authQueryService.selectMemberIdByLoginId(orderSelectSearchDTO.getMemberId());
        orderSelectSearchDTO.setMemberId(memberId);

        if ("대기".equals(orderSelectSearchDTO.getStatus())) {
            orderSelectSearchDTO.setStatus("WAIT");
        }
        if ("승인".equals(orderSelectSearchDTO.getStatus())) {
            orderSelectSearchDTO.setStatus("APPROVED");
        }
        if ("취소".equals(orderSelectSearchDTO.getStatus())) {
            orderSelectSearchDTO.setStatus("CANCEL");
        }

        int offset = Math.toIntExact(pageable.getOffset());
        int pageSize = pageable.getPageSize();
        // 정렬 정보 가져오기
        Sort sort = pageable.getSort();
        String sortField = null;
        String sortOrder = null;
        if (sort.isSorted()) {
            sortField = sort.iterator().next().getProperty();
            sortOrder = sort.iterator().next().isAscending() ? "ASC" : "DESC";
        }

        List<OrderSelectSearchDTO> orders = orderMapper.findSearchOrderByMemberId(offset, pageSize, orderSelectSearchDTO, sortField, sortOrder);

        if (orders == null) {
            throw new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND);
        }

        for (OrderSelectSearchDTO order : orders) {
            if (order.getMemberId() != null) {
                String memberName = memberQueryService.selectNameById(order.getMemberId());
                order.setMemberName(memberName);
            }

            if (order.getAdminId() != null) {
                String adminName = memberQueryService.selectNameById(order.getAdminId());
                order.setAdminName(adminName);
            } else {
                order.setAdminName("-");
            }
        }

        int count = orderMapper.findOrderSearchCountByMemberId(orderSelectSearchDTO);
        if (count == 0) {
            throw new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND);
        }

        return new PageImpl<>(orders, pageable, count);
    }

    // 영업담당자, 영업관리자 조회
    @Override
    @Transactional(readOnly = true)
    public Page<OrderSelectAllDTO> selectAll(Pageable pageable) {

        int offset = Math.toIntExact(pageable.getOffset());
        int pageSize = pageable.getPageSize();

        // 캐시 조회
        List<OrderSelectAllDTO> orders = orderMapper.findAllOrder(offset, pageSize);

        // 결과가 null이거나 빈 리스트인지 확인
        if (orders == null || orders.isEmpty()) {
            throw new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND);
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
    public Page<OrderSelectSearchDTO> selectSearchOrders(OrderSelectSearchDTO orderSelectSearchDTO, Pageable pageable) throws GeneralSecurityException {

        if ("대기".equals(orderSelectSearchDTO.getStatus())) {
            orderSelectSearchDTO.setStatus("WAIT");
        }
        if ("승인".equals(orderSelectSearchDTO.getStatus())) {
            orderSelectSearchDTO.setStatus("APPROVED");
        }
        if ("취소".equals(orderSelectSearchDTO.getStatus())) {
            orderSelectSearchDTO.setStatus("CANCEL");
        }

        int offset = Math.toIntExact(pageable.getOffset());
        int pageSize = pageable.getPageSize();

        // 정렬 정보 가져오기
        Sort sort = pageable.getSort();
        String sortField = null;
        String sortOrder = null;
        if (sort.isSorted()) {
            sortField = sort.iterator().next().getProperty();
            sortOrder = sort.iterator().next().isAscending() ? "ASC" : "DESC";
        }

        List<OrderSelectSearchDTO> orders = orderMapper.findSearchOrder(offset, pageSize, orderSelectSearchDTO, sortField, sortOrder);

        if (orders == null || orders.isEmpty()) {
            throw new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND);
        }

        for (OrderSelectSearchDTO order : orders) {
            if (order.getMemberId() != null) {
                String memberName = memberQueryService.selectNameById(order.getMemberId());
                order.setMemberName(memberName);
            }

            if (order.getAdminId() != null) {
                String adminName = memberQueryService.selectNameById(order.getAdminId());
                order.setAdminName(adminName);
            } else {
                order.setAdminName("-");
            }
        }

        Integer count = orderMapper.findOrderSearchCount(orderSelectSearchDTO);
        int totalOrder = (count != null) ? count : 0;

        return new PageImpl<>(orders, pageable, totalOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public String selectByContractId(String orderId) {

        String contractId = orderMapper.selectByContractId(orderId);

        return contractId;
    }

    @Override
    @Transactional(readOnly = true)
    public void exportOrder(HttpServletResponse response) {
        List<OrderExcelDTO> orderExcels = orderMapper.findOrderForExcel();

        if (orderExcels == null) {
            throw new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND);
        }

        excelUtilsV1.download(OrderExcelDTO.class, orderExcels, "orderExcel", response);
    }

    @Override
    public Page<OrderSelectAllDTO> selectAllCenter(Pageable pageable, String memberId) throws GeneralSecurityException {

        String memberId1 = authQueryService.selectMemberIdByLoginId(memberId);
        String centerId = memberQueryService.selectMemberInfo(memberId).getCenterId();

        int offset = Math.toIntExact(pageable.getOffset());
        int pageSize = pageable.getPageSize();

        // 캐시 조회
        List<OrderSelectAllDTO> orders = orderMapper.findAllOrderCenter(offset, pageSize, memberId1, centerId);

        // 결과가 null이거나 빈 리스트인지 확인
        if (orders == null || orders.isEmpty()) {
            throw new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND);
        }

        // 전체 개수 조회
        Integer count = orderMapper.findOrderCountCenter();
        int totalOrder = (count != null) ? count : 0;

        return new PageImpl<>(orders, pageable, totalOrder);
    }

    @Override
    public Page<OrderSelectSearchDTO> selectSearchOrdersCenter(OrderSelectSearchDTO orderSelectSearchDTO, Pageable pageable) throws GeneralSecurityException {

        String memberId = authQueryService.selectMemberIdByLoginId(orderSelectSearchDTO.getMemberId());
        String centerId = memberQueryService.selectMemberInfo(orderSelectSearchDTO.getMemberId()).getCenterId();
        orderSelectSearchDTO.setMemberId(memberId);
        orderSelectSearchDTO.setCenterId(centerId);

        if ("대기".equals(orderSelectSearchDTO.getStatus())) {
            orderSelectSearchDTO.setStatus("WAIT");
        }
        if ("승인".equals(orderSelectSearchDTO.getStatus())) {
            orderSelectSearchDTO.setStatus("APPROVED");
        }
        if ("취소".equals(orderSelectSearchDTO.getStatus())) {
            orderSelectSearchDTO.setStatus("CANCEL");
        }

        int offset = Math.toIntExact(pageable.getOffset());
        int pageSize = pageable.getPageSize();

        // 정렬 정보 가져오기
        Sort sort = pageable.getSort();
        String sortField = null;
        String sortOrder = null;
        if (sort.isSorted()) {
            sortField = sort.iterator().next().getProperty();
            sortOrder = sort.iterator().next().isAscending() ? "ASC" : "DESC";
        }

        List<OrderSelectSearchDTO> orders = orderMapper.findSearchOrderCenter(offset, pageSize, orderSelectSearchDTO, sortField, sortOrder);

        if (orders == null || orders.isEmpty()) {
            throw new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND);
        }

        for (OrderSelectSearchDTO order : orders) {
            if (order.getMemberId() != null) {
                String memberName = memberQueryService.selectNameById(order.getMemberId());
                order.setMemberName(memberName);
            }

            if (order.getAdminId() != null) {
                String adminName = memberQueryService.selectNameById(order.getAdminId());
                order.setAdminName(adminName);
            } else {
                order.setAdminName("-");
            }
        }

        Integer count = orderMapper.findOrderSearchCountCenter(orderSelectSearchDTO);
        int totalOrder = (count != null) ? count : 0;

        return new PageImpl<>(orders, pageable, totalOrder);
    }
}
