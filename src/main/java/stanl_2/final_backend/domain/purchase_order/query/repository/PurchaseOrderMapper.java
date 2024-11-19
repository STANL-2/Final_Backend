package stanl_2.final_backend.domain.purchase_order.query.repository;

import org.apache.ibatis.annotations.Mapper;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectAllDTO;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectIdDTO;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectSearchDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface PurchaseOrderMapper {
    PurchaseOrderSelectIdDTO findPurchaseOrderById(String purchaseOrderId);

    List<PurchaseOrderSelectAllDTO> findAllPurchaseOrder(int offset, int pageSize);

    int findAllPurchaseOrderCount();

    List<PurchaseOrderSelectSearchDTO> findSearchPurchaseOrder(Map<String, Object> map);

    int findSearchPurchaseOrderCount();
}
