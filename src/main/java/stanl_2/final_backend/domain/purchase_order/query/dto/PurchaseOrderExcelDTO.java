package stanl_2.final_backend.domain.purchase_order.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import stanl_2.final_backend.global.excel.ExcelColumnName;

@Getter
@Setter
@AllArgsConstructor
public class PurchaseOrderExcelDTO {

    @ExcelColumnName(name = "발주서 번호")
    private String purchaseOrderId;

    @ExcelColumnName(name = "제목")
    private String title;

    @ExcelColumnName(name = "승인 상태")
    private String status;

    @ExcelColumnName(name = "수주자")
    private String memberName;

    @ExcelColumnName(name = "제품명")
    private String productName;

    @ExcelColumnName(name = "수주일")
    private String createdAt;
}
