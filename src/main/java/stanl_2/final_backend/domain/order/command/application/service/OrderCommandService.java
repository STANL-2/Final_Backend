package stanl_2.final_backend.domain.order.command.application.service;

import stanl_2.final_backend.domain.order.command.application.dto.OrderModifyDTO;
import stanl_2.final_backend.domain.order.command.application.dto.OrderRegistDTO;
import stanl_2.final_backend.domain.order.command.application.dto.OrderStatusModifyDTO;

public interface OrderCommandService {
    void registerOrder(OrderRegistDTO orderRegistDTO);

    OrderModifyDTO modifyOrder(OrderModifyDTO orderModifyDTO);

    void deleteOrder(String orderId, String loginId);

    void modifyOrderStatus(OrderStatusModifyDTO orderStatusModifyDTO);
}
