package stanl_2.final_backend.domain.purchase_order.query.repository;

import org.apache.ibatis.annotations.Mapper;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectAllDTO;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectIdDTO;

import java.util.List;

@Mapper
public interface PurchaseOrderMapper {
    PurchaseOrderSelectIdDTO findPurchaseOrderById(String purchaseOrderId);

    List<PurchaseOrderSelectAllDTO> findAllPurchaseOrder(int offset, int pageSize);

    int findAllPurchaseOrderCount();
}
