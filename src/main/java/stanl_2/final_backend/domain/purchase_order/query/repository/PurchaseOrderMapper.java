package stanl_2.final_backend.domain.purchase_order.query.repository;

import org.apache.ibatis.annotations.Mapper;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectIdDTO;

@Mapper
public interface PurchaseOrderMapper {
    PurchaseOrderSelectIdDTO findPurchaseOrderByIdAndMemberId(String purchaseOrderId, String memberId);

    PurchaseOrderSelectIdDTO findPurchaseOrderById(String purchaseOrderId);
}
