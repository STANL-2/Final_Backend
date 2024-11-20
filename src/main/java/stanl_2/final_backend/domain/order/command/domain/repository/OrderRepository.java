package stanl_2.final_backend.domain.order.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stanl_2.final_backend.domain.order.command.domain.aggregate.entity.Order;

public interface OrderRepository extends JpaRepository<Order, String> {
    Order findByOrderIdAndMemberId(String orderId, String memberId);

    Order findByOrderId(String orderId);
}
