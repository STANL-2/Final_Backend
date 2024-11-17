package stanl_2.final_backend.domain.purchase_order.command.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.order.command.domain.aggregate.entity.Order;
import stanl_2.final_backend.domain.order.command.domain.repository.OrderRepository;
import stanl_2.final_backend.domain.order.common.exception.OrderCommonException;
import stanl_2.final_backend.domain.order.common.exception.OrderErrorCode;
import stanl_2.final_backend.domain.purchase_order.command.application.dto.PurchaseOrderModifyDTO;
import stanl_2.final_backend.domain.purchase_order.command.application.dto.PurchaseOrderRegistDTO;
import stanl_2.final_backend.domain.purchase_order.command.application.service.PurchaseOrderCommandService;
import stanl_2.final_backend.domain.purchase_order.command.domain.aggregate.entity.PurchaseOrder;
import stanl_2.final_backend.domain.purchase_order.command.domain.repository.PurchaseOrderRepository;
import stanl_2.final_backend.domain.purchase_order.common.exception.PurchaseOrderCommonException;
import stanl_2.final_backend.domain.purchase_order.common.exception.PurchaseOrderErrorCode;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PurchaseOrderCommandServiceImpl implements PurchaseOrderCommandService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PurchaseOrderCommandServiceImpl(PurchaseOrderRepository purchaseOrderRepository, OrderRepository orderRepository, ModelMapper modelMapper) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    private String  getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    @Transactional
    public void registerPurchaseOrder(PurchaseOrderRegistDTO purchaseOrderRegistDTO) {

        // 수주서가 존재하는지 확인
        Order order = orderRepository.findByOrderIdAndMemberId(
                purchaseOrderRegistDTO.getOrderId(), purchaseOrderRegistDTO.getMemberId());
        if (order == null) {
            throw new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND);
        }

        // 수주서의 상태가 APPROVED인지 확인
        if (!"APPROVED".equals(order.getStatus())) {
            throw new OrderCommonException(OrderErrorCode.ORDER_STATUS_NOT_APPROVED);
        }

        PurchaseOrder purchaseOrder = modelMapper.map(purchaseOrderRegistDTO, PurchaseOrder.class);
        purchaseOrder.setStatus("WAIT");

        purchaseOrderRepository.save(purchaseOrder);
    }

    @Override
    @Transactional
    public PurchaseOrderModifyDTO modifyPurchaseOrder(PurchaseOrderModifyDTO purchaseOrderModifyDTO) {

        // 회원인지 확인 및 발주서 조회
        PurchaseOrder purchaseOrder = (PurchaseOrder) purchaseOrderRepository.findByPurchaseOrderIdAndMemberId(
                        purchaseOrderModifyDTO.getPurchaseOrderId(), purchaseOrderModifyDTO.getMemberId())
                .orElseThrow(() -> new PurchaseOrderCommonException(PurchaseOrderErrorCode.PURCHASE_ORDER_NOT_FOUND));

        // 수주서가 존재하는지 확인
        Order order = orderRepository.findByOrderIdAndMemberId(
                purchaseOrderModifyDTO.getOrderId(), purchaseOrderModifyDTO.getMemberId());
        if (order == null) {
            throw new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND);
        }

        // 수주서의 상태가 APPROVED인지 확인
        if (!"APPROVED".equals(order.getStatus())) {
            throw new OrderCommonException(OrderErrorCode.ORDER_STATUS_NOT_APPROVED);
        }

        PurchaseOrder updatePurchaseOrder = modelMapper.map(purchaseOrderModifyDTO, PurchaseOrder.class);

        updatePurchaseOrder.setCreatedAt(purchaseOrder.getCreatedAt());
        updatePurchaseOrder.setUpdatedAt(purchaseOrder.getUpdatedAt());
        updatePurchaseOrder.setStatus(purchaseOrder.getStatus());
        updatePurchaseOrder.setActive(purchaseOrder.getActive());

        purchaseOrderRepository.save(updatePurchaseOrder);

        PurchaseOrderModifyDTO purchaseOrderModifyResponse = modelMapper.map(updatePurchaseOrder, PurchaseOrderModifyDTO.class);

        return purchaseOrderModifyResponse;
    }

    @Override
    @Transactional
    public void deletePurchaseOrder(String id) {
        // 발주서가 해당 회원의 것인지 확인 (회원도 받아와서 하는걸로 나중에 수정)
//        PurchaseOrder purchaseOrder = purchaseOrderRepository.findByIdAndMemberId(id, memberId)
//                .orElseThrow(() -> new PurchaseOrderCommonException(PurchaseOrderErrorCode.PURCHASE_ORDER_NOT_FOUND));

        PurchaseOrder purchaseOrder = (PurchaseOrder) purchaseOrderRepository.findByPurchaseOrderId(id)
                .orElseThrow(() -> new PurchaseOrderCommonException(PurchaseOrderErrorCode.PURCHASE_ORDER_NOT_FOUND));

        purchaseOrder.setActive(false);
        purchaseOrder.setDeletedAt(getCurrentTime());

        purchaseOrderRepository.save(purchaseOrder);
    }
}
