package stanl_2.final_backend.domain.order.command.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.contract.command.domain.repository.ContractRepository;
import stanl_2.final_backend.domain.order.command.application.dto.OrderModifyDTO;
import stanl_2.final_backend.domain.order.command.application.dto.OrderRegistDTO;
import stanl_2.final_backend.domain.order.command.application.service.OrderCommandService;
import stanl_2.final_backend.domain.order.command.domain.aggregate.entity.Order;
import stanl_2.final_backend.domain.order.command.domain.repository.OrderRepository;
import stanl_2.final_backend.domain.order.common.exception.OrderCommonException;
import stanl_2.final_backend.domain.order.common.exception.OrderErrorCode;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderCommandServiceImpl implements OrderCommandService {

    private final OrderRepository orderRepository;
    private final ContractRepository contractRepository;
    private final ModelMapper modelMapper;

    public OrderCommandServiceImpl(OrderRepository orderRepository, ContractRepository contractRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.contractRepository = contractRepository;
        this.modelMapper = modelMapper;
    }

    private String  getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    @Transactional
    public void registerOrder(OrderRegistDTO orderRegistDTO) {
        // 회원인지 확인여부

        // 회원의 계약서가 맞는지 확인
//        Contract contract = contractRepository.findById(orderRegistDTO.getConrId())
//                .orElseThrow(() -> new Contrac)

        Order order = modelMapper.map(orderRegistDTO, Order.class);
        order.setStatus("WAIT");

        orderRepository.save(order);
    }

    @Override
    @Transactional
    public OrderModifyDTO modifyOrder(OrderModifyDTO orderModifyDTO) {
        // 회원인지 확인여부

        Order order = orderRepository.findById(orderModifyDTO.getOrderId())
                .orElseThrow(() -> new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND));

        Order updateOrder = modelMapper.map(orderModifyDTO, Order.class);
        updateOrder.setCreatedAt(order.getCreatedAt());
        updateOrder.setUpdatedAt(order.getUpdatedAt());
        updateOrder.setStatus(order.getStatus());
        updateOrder.setActive(order.getActive());
        updateOrder.setContractId(order.getContractId());

        orderRepository.save(updateOrder);

        OrderModifyDTO orderModifyResponse = modelMapper.map(updateOrder, OrderModifyDTO.class);

        return orderModifyResponse;
    }

    @Override
    @Transactional
    public void deleteOrder(String id) {

        // 회원 확인

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND));

        order.setActive(false);
        order.setDeletedAt(getCurrentTime());

        orderRepository.save(order);
    }
}
