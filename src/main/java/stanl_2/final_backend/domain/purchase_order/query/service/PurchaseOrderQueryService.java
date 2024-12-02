package stanl_2.final_backend.domain.purchase_order.query.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectAllDTO;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectIdDTO;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectSearchDTO;

import java.security.GeneralSecurityException;

public interface PurchaseOrderQueryService {
    PurchaseOrderSelectIdDTO selectDetailPurchaseOrderAdmin(PurchaseOrderSelectIdDTO purchaseOrderSelectIdDTO);

    Page<PurchaseOrderSelectAllDTO> selectAllPurchaseOrderAdmin(Pageable pageable, PurchaseOrderSelectAllDTO purchaseOrderSelectAllDTO);

    Page<PurchaseOrderSelectSearchDTO> selectSearchPurchaseOrderAdmin(PurchaseOrderSelectSearchDTO purchaseOrderSelectSearchDTO, Pageable pageable) throws GeneralSecurityException;

    Page<PurchaseOrderSelectSearchDTO> selectSearchPurchaseOrder(PurchaseOrderSelectSearchDTO purchaseOrderSelectSearchDTO, Pageable pageable) throws GeneralSecurityException;

    Page<PurchaseOrderSelectAllDTO> selectAllPurchaseOrder(Pageable pageable, PurchaseOrderSelectAllDTO purchaseOrderSelectAllDTO);

    PurchaseOrderSelectIdDTO selectDetailPurchaseOrder(PurchaseOrderSelectIdDTO purchaseOrderSelectIdDTO);

    void exportPurchaseOrder(HttpServletResponse response) throws GeneralSecurityException;
}
