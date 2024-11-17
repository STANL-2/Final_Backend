package stanl_2.final_backend.domain.purchase_order.command.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.order.command.domain.aggregate.entity.Order;
import stanl_2.final_backend.domain.order.command.domain.repository.OrderRepository;
import stanl_2.final_backend.domain.order.common.exception.OrderCommonException;
import stanl_2.final_backend.domain.order.common.exception.OrderErrorCode;
import stanl_2.final_backend.domain.purchase_order.command.application.dto.PurchaseOrderRegistDTO;
import stanl_2.final_backend.domain.purchase_order.command.application.service.PurchaseOrderCommandService;
import stanl_2.final_backend.domain.purchase_order.command.domain.aggregate.entity.PurchaseOrder;
import stanl_2.final_backend.domain.purchase_order.command.domain.repository.PurchaseOrderRepository;

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

    @Override
    @Transactional
    public void registerPurchaseOrder(PurchaseOrderRegistDTO purchaseOrderRegistDTO) {

        // 회원인지 확인

        // 수주서가 맞는지
        Order orderCheck = orderRepository.findByOrderIdAndMemberId(purchaseOrderRegistDTO.getOrderId(), purchaseOrderRegistDTO.getMemberId());

        if(orderCheck == null) {
            throw new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND);
        }

        // 수주서의 승인상태가 APPROVED인지 확인
        Order orderStatus = orderRepository.findByOrderIdAndStatus(orderCheck.getOrderId(), "APPROVED");

        if(orderStatus == null) {
            throw new OrderCommonException(OrderErrorCode.ORDER_STATUS_NOT_APPROVED);
        }

        PurchaseOrder purchaseOrder = modelMapper.map(purchaseOrderRegistDTO, PurchaseOrder.class);
        purchaseOrder.setStatus("WAIT");

        purchaseOrderRepository.save(purchaseOrder);
    }
}
