package stanl_2.final_backend.domain.notices.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import stanl_2.final_backend.global.excel.ExcelColumnName;

@Getter
@AllArgsConstructor
public class NoticeExcelDownload {
    @ExcelColumnName(name = "제목")
    private String title;

    @ExcelColumnName(name = "태그")
    private String tag;

    @ExcelColumnName(name = "분류")
    private String classification;

    @ExcelColumnName(name = "내용")
    private String content;

    @ExcelColumnName(name = "생성일자")
    private String createdAt;

    @ExcelColumnName(name = "수정일자")
    private String updatedAt;

    @ExcelColumnName(name = "작성자")
    private String memberId;
}
