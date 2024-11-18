package stanl_2.final_backend.domain.purchase_order.query.service;

import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectIdDTO;

public interface PurchaseOrderQueryService {
    PurchaseOrderSelectIdDTO selectDetailPurchaseOrder(PurchaseOrderSelectIdDTO purchaseOrderSelectIdDTO);
}
