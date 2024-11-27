package stanl_2.final_backend.domain.center.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import stanl_2.final_backend.global.excel.ExcelColumnName;

@Getter
@AllArgsConstructor
public class CenterExcelDownload {

    @ExcelColumnName(name = "매장번호")
    private String centerId;

    @ExcelColumnName(name = "지점 이름")
    private String name;

    @ExcelColumnName(name = "주소")
    private String address;

    @ExcelColumnName(name = "사원 수")
    private Integer memberCount;

    @ExcelColumnName(name = "운영시간")
    private String operatingAt;

}
