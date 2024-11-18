package stanl_2.final_backend.domain.purchase_order.command.application.service;

import org.springframework.security.core.GrantedAuthority;
import stanl_2.final_backend.domain.purchase_order.command.application.dto.PurchaseOrderModifyDTO;
import stanl_2.final_backend.domain.purchase_order.command.application.dto.PurchaseOrderRegistDTO;
import stanl_2.final_backend.domain.purchase_order.command.application.dto.PurchaseOrderStatusModifyDTO;

import java.util.Collection;

public interface PurchaseOrderCommandService {
    void registerPurchaseOrder(PurchaseOrderRegistDTO purchaseOrderRegistDTO);

    PurchaseOrderModifyDTO modifyPurchaseOrder(PurchaseOrderModifyDTO purchaseOrderModifyDTO);

    void deletePurchaseOrder(String id, String loginId);

    void modifyPurchaseOrderStatus(PurchaseOrderStatusModifyDTO purchaseOrderStatusModifyDTO);
}
