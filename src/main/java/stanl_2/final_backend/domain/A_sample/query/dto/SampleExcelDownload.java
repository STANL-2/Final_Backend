package stanl_2.final_backend.domain.A_sample.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import stanl_2.final_backend.global.excel.ExcelColumnName;

@Getter
@AllArgsConstructor
public class SampleExcelDownload {

    @ExcelColumnName(name = "이름")
    private String name;

    @ExcelColumnName(name = "갯수")
    private String num;

    @ExcelColumnName(name = "판매상태")
    private Boolean  active;

    @ExcelColumnName(name = "생성일자")
    private String createdAt;
}
