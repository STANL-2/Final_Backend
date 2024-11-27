package stanl_2.final_backend.domain.evaluation.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import stanl_2.final_backend.global.excel.ExcelColumnName;

@AllArgsConstructor
@Getter
@Setter
public class EvaluationExcelDownload {
    @ExcelColumnName(name = "평가서번호")
    private String evalId;

    @ExcelColumnName(name = "제목")
    private String title;

    @ExcelColumnName(name = "매장이름")
    private String centerId;

    @ExcelColumnName(name = "사원이름")
    private String memberId;

    @ExcelColumnName(name = "평가작성자")
    private String writerId;

    @ExcelColumnName(name = "작성일자")
    private String createdAt;
}
