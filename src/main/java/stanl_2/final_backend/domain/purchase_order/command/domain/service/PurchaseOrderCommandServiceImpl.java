package stanl_2.final_backend.domain.purchase_order.command.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.alarm.command.application.service.AlarmCommandService;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.order.command.domain.aggregate.entity.Order;
import stanl_2.final_backend.domain.order.command.domain.repository.OrderRepository;
import stanl_2.final_backend.domain.order.common.exception.OrderCommonException;
import stanl_2.final_backend.domain.order.common.exception.OrderErrorCode;
import stanl_2.final_backend.domain.purchase_order.command.application.dto.PurchaseOrderAlarmDTO;
import stanl_2.final_backend.domain.purchase_order.command.application.dto.PurchaseOrderModifyDTO;
import stanl_2.final_backend.domain.purchase_order.command.application.dto.PurchaseOrderRegistDTO;
import stanl_2.final_backend.domain.purchase_order.command.application.dto.PurchaseOrderStatusModifyDTO;
import stanl_2.final_backend.domain.purchase_order.command.application.service.PurchaseOrderCommandService;
import stanl_2.final_backend.domain.purchase_order.command.domain.aggregate.entity.PurchaseOrder;
import stanl_2.final_backend.domain.purchase_order.command.domain.repository.PurchaseOrderRepository;
import stanl_2.final_backend.domain.purchase_order.common.exception.PurchaseOrderCommonException;
import stanl_2.final_backend.domain.purchase_order.common.exception.PurchaseOrderErrorCode;
import stanl_2.final_backend.domain.s3.command.application.service.S3FileService;

import java.security.GeneralSecurityException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class PurchaseOrderCommandServiceImpl implements PurchaseOrderCommandService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final OrderRepository orderRepository;
    private final AuthQueryService authQueryService;
    private final ModelMapper modelMapper;
    private final AlarmCommandService alarmCommandService;
    private final S3FileService s3FileService;

    @Autowired
    public PurchaseOrderCommandServiceImpl(PurchaseOrderRepository purchaseOrderRepository, OrderRepository orderRepository,
                                           AuthQueryService authQueryService, ModelMapper modelMapper,
                                           AlarmCommandService alarmCommandService,S3FileService s3FileService) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.orderRepository = orderRepository;
        this.authQueryService = authQueryService;
        this.modelMapper = modelMapper;
        this.alarmCommandService = alarmCommandService;
        this.s3FileService = s3FileService;
    }

    private String  getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    @Transactional
    public void registerPurchaseOrder(PurchaseOrderRegistDTO purchaseOrderRegistDTO) {

        String memberId = authQueryService.selectMemberIdByLoginId(purchaseOrderRegistDTO.getMemberId());

        // 수주서가 존재하는지 확인
        Order order = orderRepository.findByOrderId(purchaseOrderRegistDTO.getOrderId());

        if (order == null) {
            throw new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND);
        }

        // 수주서의 상태가 APPROVED인지 확인
        if (!"APPROVED".equals(order.getStatus())) {
            throw new OrderCommonException(OrderErrorCode.ORDER_STATUS_NOT_APPROVED);
        }

        String unescapedHtml = StringEscapeUtils.unescapeJson(purchaseOrderRegistDTO.getContent());
        String updatedS3Url = s3FileService.uploadHtml(unescapedHtml, purchaseOrderRegistDTO.getTitle());

        PurchaseOrder purchaseOrder = modelMapper.map(purchaseOrderRegistDTO, PurchaseOrder.class);
        purchaseOrder.setMemberId(memberId);
        purchaseOrder.setContent(updatedS3Url);

        purchaseOrderRepository.save(purchaseOrder);
    }

    @Override
    @Transactional
    public PurchaseOrderModifyDTO modifyPurchaseOrder(PurchaseOrderModifyDTO purchaseOrderModifyDTO) {

        String memberId = authQueryService.selectMemberIdByLoginId(purchaseOrderModifyDTO.getMemberId());

        // 회원인지 확인 및 발주서 조회
        PurchaseOrder purchaseOrder = (PurchaseOrder) purchaseOrderRepository.findByPurchaseOrderIdAndMemberId(
                        purchaseOrderModifyDTO.getPurchaseOrderId(), memberId)
                .orElseThrow(() -> new PurchaseOrderCommonException(PurchaseOrderErrorCode.PURCHASE_ORDER_NOT_FOUND));

        // 수주서가 존재하는지 확인
        Order order = orderRepository.findByOrderIdAndMemberId(purchaseOrder.getOrderId(), memberId);
        if (order == null) {
            throw new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND);
        }

        // 수주서의 상태가 APPROVED인지 확인
        if (!"APPROVED".equals(order.getStatus())) {
            throw new OrderCommonException(OrderErrorCode.ORDER_STATUS_NOT_APPROVED);
        }

        String unescapedHtml = StringEscapeUtils.unescapeJson(purchaseOrderModifyDTO.getContent());
        String updatedS3Url = s3FileService.uploadHtml(unescapedHtml, purchaseOrderModifyDTO.getTitle());

        PurchaseOrder updatePurchaseOrder = modelMapper.map(purchaseOrderModifyDTO, PurchaseOrder.class);
        updatePurchaseOrder.setCreatedAt(purchaseOrder.getCreatedAt());
        updatePurchaseOrder.setUpdatedAt(purchaseOrder.getUpdatedAt());
        updatePurchaseOrder.setStatus(purchaseOrder.getStatus());
        updatePurchaseOrder.setActive(purchaseOrder.getActive());
        updatePurchaseOrder.setContent(updatedS3Url);
        updatePurchaseOrder.setOrderId(purchaseOrder.getOrderId());
        updatePurchaseOrder.setMemberId(memberId);

        purchaseOrderRepository.save(updatePurchaseOrder);

        PurchaseOrderModifyDTO purchaseOrderModifyResponse = modelMapper.map(updatePurchaseOrder, PurchaseOrderModifyDTO.class);

        return purchaseOrderModifyResponse;
    }

    @Override
    @Transactional
    public void deletePurchaseOrder(String purchaseOrderId, String loginId) {

        String memberId = authQueryService.selectMemberIdByLoginId(loginId);

        // 발주서가 해당 회원의 것인지 확인
        PurchaseOrder purchaseOrder = (PurchaseOrder) purchaseOrderRepository.findByPurchaseOrderIdAndMemberId(purchaseOrderId, memberId)
                .orElseThrow(() -> new PurchaseOrderCommonException(PurchaseOrderErrorCode.PURCHASE_ORDER_NOT_FOUND));

        purchaseOrder.setActive(false);
        purchaseOrder.setDeletedAt(getCurrentTime());

        purchaseOrderRepository.save(purchaseOrder);
    }

    @Override
    @Transactional
    public void modifyPurchaseOrderStatus(PurchaseOrderStatusModifyDTO purchaseOrderStatusModifyDTO) throws GeneralSecurityException {

        String adminId = authQueryService.selectMemberIdByLoginId(purchaseOrderStatusModifyDTO.getAdminId());

        PurchaseOrder purchaseOrder = purchaseOrderRepository.findByPurchaseOrderId(purchaseOrderStatusModifyDTO.getPurchaseOrderId())
                .orElseThrow(() -> new PurchaseOrderCommonException(PurchaseOrderErrorCode.PURCHASE_ORDER_NOT_FOUND));

        purchaseOrder.setStatus(purchaseOrderStatusModifyDTO.getStatus());
        purchaseOrder.setAdminId(adminId);


        purchaseOrderRepository.save(purchaseOrder);

        PurchaseOrderAlarmDTO purchaseOrderAlarmDTO = new PurchaseOrderAlarmDTO(purchaseOrder.getPurchaseOrderId(),
                purchaseOrder.getTitle(), purchaseOrder.getMemberId(), purchaseOrder.getAdminId());

        alarmCommandService.sendPurchaseOrderAlarm(purchaseOrderAlarmDTO);
    }
}
