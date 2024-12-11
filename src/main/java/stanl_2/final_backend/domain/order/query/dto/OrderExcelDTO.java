package stanl_2.final_backend.domain.order.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import stanl_2.final_backend.global.excel.ExcelColumnName;

@Getter
@AllArgsConstructor
public class OrderExcelDTO {

    @ExcelColumnName(name = "수주서 번호")
    private String orderId;

    @ExcelColumnName(name = "수주서명")
    private String title;

    @ExcelColumnName(name = "승인 상태")
    private String status;

    @ExcelColumnName(name = "제품명")
    private String productName;

    @ExcelColumnName(name = "수주자")
    private String memberId;

    @ExcelColumnName(name = "수주일")
    private String createdAt;
}
