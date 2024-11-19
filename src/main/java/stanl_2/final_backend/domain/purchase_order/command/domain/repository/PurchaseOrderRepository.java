package stanl_2.final_backend.domain.purchase_order.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stanl_2.final_backend.domain.purchase_order.command.domain.aggregate.entity.PurchaseOrder;

import java.util.Optional;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, String> {
    Optional<Object> findByPurchaseOrderIdAndMemberId(String purchaseOrderId, String memberId);

    Optional<PurchaseOrder> findByPurchaseOrderId(String id);
}
