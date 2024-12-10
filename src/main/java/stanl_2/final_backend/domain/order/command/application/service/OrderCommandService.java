package stanl_2.final_backend.domain.order.command.application.service;

import stanl_2.final_backend.domain.order.command.application.dto.OrderModifyDTO;
import stanl_2.final_backend.domain.order.command.application.dto.OrderRegistDTO;
import stanl_2.final_backend.domain.order.command.application.dto.OrderStatusModifyDTO;

import java.security.GeneralSecurityException;

public interface OrderCommandService {
    void registerOrder(OrderRegistDTO orderRegistDTO) throws GeneralSecurityException;

    OrderModifyDTO modifyOrder(OrderModifyDTO orderModifyDTO) throws GeneralSecurityException;

    void deleteOrder(String orderId, String loginId);

    void modifyOrderStatus(OrderStatusModifyDTO orderStatusModifyDTO) throws GeneralSecurityException;
}
