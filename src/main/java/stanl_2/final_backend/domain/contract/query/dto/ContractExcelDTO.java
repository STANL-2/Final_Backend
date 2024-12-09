package stanl_2.final_backend.domain.contract.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stanl_2.final_backend.global.excel.ExcelColumnName;

@Getter
@AllArgsConstructor
public class ContractExcelDTO {

    @ExcelColumnName(name = "계약서 번호")
    private String contractId;

    @ExcelColumnName(name = "계약서명")
    private String title;

    @ExcelColumnName(name = "고객명")
    private String customerName;

    @ExcelColumnName(name = "승인 상태")
    private String status;

    @ExcelColumnName(name = "제품명")
    private String carName;

    @ExcelColumnName(name = "고객 조건")
    private String customerPurchaseCondition;

    @ExcelColumnName(name = "계약일자")
    private String createdAt;
}
