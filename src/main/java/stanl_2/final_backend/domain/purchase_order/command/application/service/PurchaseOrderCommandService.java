package stanl_2.final_backend.domain.purchase_order.command.application.service;

import stanl_2.final_backend.domain.purchase_order.command.application.dto.PurchaseOrderModifyDTO;
import stanl_2.final_backend.domain.purchase_order.command.application.dto.PurchaseOrderRegistDTO;

public interface PurchaseOrderCommandService {
    void registerPurchaseOrder(PurchaseOrderRegistDTO purchaseOrderRegistDTO);

    PurchaseOrderModifyDTO modifyPurchaseOrder(PurchaseOrderModifyDTO purchaseOrderModifyDTO);
}
