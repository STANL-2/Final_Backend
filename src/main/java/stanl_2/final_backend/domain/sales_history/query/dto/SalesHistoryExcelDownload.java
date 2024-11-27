package stanl_2.final_backend.domain.sales_history.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import stanl_2.final_backend.global.excel.ExcelColumnName;

@Getter
@AllArgsConstructor
@Setter
public class SalesHistoryExcelDownload {

    @ExcelColumnName(name = "판매내역번호")
    private String salesHistoryId;
    @ExcelColumnName(name = "차량대수")
    private Integer salesHistoryNumberOfVehicles;
    @ExcelColumnName(name = "매출액")
    private Integer salesHistoryTotalSales;
    @ExcelColumnName(name = "수당")
    private Integer salesHistoryIncentive;
    @ExcelColumnName(name = "작성일자")
    private String createdAt;
    @ExcelColumnName(name = "계약서번호")
    private String contractId;
    @ExcelColumnName(name = "고객번호")
    private String customerId;
    @ExcelColumnName(name = "제품번호")
    private String productId;
    @ExcelColumnName(name = "담당자번호")
    private String memberId;
    @ExcelColumnName(name = "매장번호")
    private String centerId;
}
