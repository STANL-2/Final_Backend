package stanl_2.final_backend.domain.purchase_order.query.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectAllDTO;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectIdDTO;

public interface PurchaseOrderQueryService {
    PurchaseOrderSelectIdDTO selectDetailPurchaseOrder(PurchaseOrderSelectIdDTO purchaseOrderSelectIdDTO);

    Page<PurchaseOrderSelectAllDTO> selectAllPurchaseOrder(Pageable pageable, PurchaseOrderSelectAllDTO purchaseOrderSelectAllDTO);
}
