package stanl_2.final_backend.domain.purchase_order.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stanl_2.final_backend.domain.purchase_order.command.application.dto.PurchaseOrderExcelDTO;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectAllDTO;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectIdDTO;
import stanl_2.final_backend.domain.purchase_order.query.dto.PurchaseOrderSelectSearchDTO;

import java.util.List;

@Mapper
public interface PurchaseOrderMapper {
    PurchaseOrderSelectIdDTO findPurchaseOrderByPurchaseOrderIdAndMemberId(@Param("purchaseOrderId") String purchaseOrderId,
                                                                           @Param("memberId") String memberId);

    List<PurchaseOrderSelectAllDTO> findAllPurchaseOrderByMemberId(@Param("offset") int offset,
                                                                   @Param("pageSize") int pageSize,
                                                                   @Param("memberId") String memberId);

    Integer findAllPurchaseOrderCountByMemberId(String memberId);

    List<PurchaseOrderSelectSearchDTO> findSearchPurchaseOrder(@Param("offset") int offset, 
                                                               @Param("pageSize") int pageSize, 
                                                               @Param("purchaseOrderSelectSearchDTO") 
                                                               PurchaseOrderSelectSearchDTO purchaseOrderSelectSearchDTO,
                                                               @Param("sortField") String sortField,
                                                               @Param("sortOrder") String sortOrder);

    List<PurchaseOrderSelectSearchDTO> findSearchPurchaseOrderMemberId(int offset, int pageSize, PurchaseOrderSelectSearchDTO purchaseOrderSelectSearchDTO);

    Integer findSearchPurchaseOrderCountMemberId(PurchaseOrderSelectSearchDTO purchaseOrderSelectSearchDTO);

    PurchaseOrderSelectIdDTO findPurchaseOrderByPurchaseOrderId(String purchaseOrderId);

    List<PurchaseOrderSelectAllDTO> findAllPurchaseOrder(@Param("pageSize") int offset,
                                                         @Param("pageSize") int pageSize);

    Integer findAllPurchaseOrderCount();

    int findSearchPurchaseOrderCount(@Param("purchaseOrderSelectSearchDTO") PurchaseOrderSelectSearchDTO purchaseOrderSelectSearchDTO);

    List<PurchaseOrderExcelDTO> findPurchaseOrderForExcel();
}
