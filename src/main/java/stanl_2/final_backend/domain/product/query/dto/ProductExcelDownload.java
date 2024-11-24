package stanl_2.final_backend.domain.product.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import stanl_2.final_backend.global.excel.ExcelColumnName;

@Getter
@AllArgsConstructor
public class ProductExcelDownload {
    @ExcelColumnName(name = "제품번호")
    private String productId;

    @ExcelColumnName(name = "제품 이름")
    private String name;

    @ExcelColumnName(name = "일련번호")
    private String serialNumber;

    @ExcelColumnName(name = "차량가액")
    private Integer cost;

    @ExcelColumnName(name = "재고 수")
    private String stock;
}
