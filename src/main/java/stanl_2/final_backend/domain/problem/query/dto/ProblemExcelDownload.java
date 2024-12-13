package stanl_2.final_backend.domain.problem.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import stanl_2.final_backend.global.excel.ExcelColumnName;

@Getter
@AllArgsConstructor
public class ProblemExcelDownload {
    @ExcelColumnName(name = "문제제목")
    private String title;

    @ExcelColumnName(name = "문제내용")
    private String content;

    @ExcelColumnName(name = "생성일자")
    private String createdAt;

    @ExcelColumnName(name = "수정일자")
    private String updatedAt;

    @ExcelColumnName(name = "작성자")
    private String memberId;

    @ExcelColumnName(name = "고객이름")
    private String customerId;

    @ExcelColumnName(name = "제품명")
    private String productId;

    @ExcelColumnName(name = "상태")
    private String status;
}
