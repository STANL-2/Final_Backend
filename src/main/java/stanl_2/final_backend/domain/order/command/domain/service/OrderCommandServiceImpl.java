package stanl_2.final_backend.domain.order.command.domain.service;

import org.apache.commons.lang3.StringEscapeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.alarm.command.application.service.AlarmCommandService;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.domain.order.command.application.dto.OrderAlarmDTO;
import stanl_2.final_backend.domain.order.command.application.dto.OrderModifyDTO;
import stanl_2.final_backend.domain.order.command.application.dto.OrderRegistDTO;
import stanl_2.final_backend.domain.order.command.application.dto.OrderStatusModifyDTO;
import stanl_2.final_backend.domain.order.command.application.service.OrderCommandService;
import stanl_2.final_backend.domain.order.command.domain.aggregate.entity.Order;
import stanl_2.final_backend.domain.order.command.domain.repository.OrderRepository;
import stanl_2.final_backend.domain.order.common.exception.OrderCommonException;
import stanl_2.final_backend.domain.order.common.exception.OrderErrorCode;
import stanl_2.final_backend.domain.s3.command.application.service.S3FileService;

import java.security.GeneralSecurityException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderCommandServiceImpl implements OrderCommandService {

    private final OrderRepository orderRepository;
    private final AuthQueryService authQueryService;
    private final ModelMapper modelMapper;
    private final S3FileService s3FileService;
    private final AlarmCommandService alarmCommandService;
    private final MemberQueryService memberQueryService;

    public OrderCommandServiceImpl(OrderRepository orderRepository, AuthQueryService authQueryService, ModelMapper modelMapper,
                                   S3FileService s3FileService , AlarmCommandService alarmCommandService, MemberQueryService memberQueryService) {
        this.orderRepository = orderRepository;
        this.authQueryService = authQueryService;
        this.modelMapper = modelMapper;
        this.s3FileService = s3FileService;
        this.alarmCommandService = alarmCommandService;
        this.memberQueryService = memberQueryService;
    }

    private String  getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    @Transactional
    public void registerOrder(OrderRegistDTO orderRegistDTO) throws GeneralSecurityException {

        String memberId = authQueryService.selectMemberIdByLoginId(orderRegistDTO.getMemberId());
        String centerId = memberQueryService.selectMemberInfo(orderRegistDTO.getMemberId()).getCenterId();

        String unescapedHtml = StringEscapeUtils.unescapeJson(orderRegistDTO.getContent());
        String updatedS3Url = s3FileService.uploadHtml(unescapedHtml, orderRegistDTO.getTitle());

        orderRegistDTO.setMemberId(memberId);
        orderRegistDTO.setContent(updatedS3Url);
        orderRegistDTO.setCenterId(centerId);

        Order order = modelMapper.map(orderRegistDTO, Order.class);

        orderRepository.save(order);
    }

    @Override
    @Transactional
    public OrderModifyDTO modifyOrder(OrderModifyDTO orderModifyDTO) throws GeneralSecurityException {

        String memberId = authQueryService.selectMemberIdByLoginId(orderModifyDTO.getMemberId());
        String centerId = memberQueryService.selectMemberInfo(orderModifyDTO.getMemberId()).getCenterId();
        orderModifyDTO.setMemberId(memberId);
        orderModifyDTO.setCenterId(centerId);

        Order order = orderRepository.findByOrderIdAndMemberId(orderModifyDTO.getOrderId(), memberId);

        if (order == null) {
            throw new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND);
        }

        String unescapedHtml = StringEscapeUtils.unescapeJson(orderModifyDTO.getContent());
        String updatedS3Url = s3FileService.uploadHtml(unescapedHtml, orderModifyDTO.getTitle());

        orderModifyDTO.setContent(updatedS3Url);

        Order updateOrder = modelMapper.map(orderModifyDTO, Order.class);
        updateOrder.setCreatedAt(order.getCreatedAt());
        updateOrder.setUpdatedAt(order.getUpdatedAt());
        updateOrder.setStatus(order.getStatus());
        updateOrder.setActive(order.getActive());

        orderRepository.save(updateOrder);

        OrderModifyDTO orderModifyResponse = modelMapper.map(updateOrder, OrderModifyDTO.class);

        return orderModifyResponse;
    }

    @Override
    @Transactional
    public void deleteOrder(String id, String loginId) {

        String memberId = authQueryService.selectMemberIdByLoginId(loginId);

        Order order = orderRepository.findByOrderIdAndMemberId(id, memberId);

        if (order == null) {
            throw new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND);
        }

        order.setActive(false);
        order.setDeletedAt(getCurrentTime());

        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void modifyOrderStatus(OrderStatusModifyDTO orderStatusModifyDTO) throws GeneralSecurityException {

        String adminId = authQueryService.selectMemberIdByLoginId(orderStatusModifyDTO.getAdminId());
        Order order = orderRepository.findByOrderId(orderStatusModifyDTO.getOrderId());
        if (order == null) {
            throw new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND);
        }

        order.setStatus(orderStatusModifyDTO.getStatus());
        order.setAdminId(adminId);

        orderRepository.save(order);

        OrderAlarmDTO orderAlarmDTO = new OrderAlarmDTO(order.getOrderId(), order.getTitle(), order.getMemberId(),
                                                        order.getAdminId());

        alarmCommandService.sendOrderAlarm(orderAlarmDTO);
    }
}
