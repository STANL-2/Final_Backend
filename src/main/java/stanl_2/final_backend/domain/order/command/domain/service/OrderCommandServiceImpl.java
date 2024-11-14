package stanl_2.final_backend.domain.order.command.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.contract.command.domain.aggregate.entity.Contract;
import stanl_2.final_backend.domain.contract.command.domain.repository.ContractRepository;
import stanl_2.final_backend.domain.order.command.application.dto.OrderRegistDTO;
import stanl_2.final_backend.domain.order.command.application.service.OrderCommandService;
import stanl_2.final_backend.domain.order.command.domain.aggregate.entity.Order;
import stanl_2.final_backend.domain.order.command.domain.repository.OrderRepository;

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
}
