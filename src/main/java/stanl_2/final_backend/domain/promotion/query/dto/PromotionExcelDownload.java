package stanl_2.final_backend.domain.promotion.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import stanl_2.final_backend.global.excel.ExcelColumnName;

@Getter
@AllArgsConstructor
public class PromotionExcelDownload {
    @ExcelColumnName(name = "프로모션 제목")
    private String title;

    @ExcelColumnName(name = "프로모션 내용")
    private String content;

    @ExcelColumnName(name = "생성일자")
    private String createdAt;

    @ExcelColumnName(name = "수정일자")
    private String updatedAt;

    @ExcelColumnName(name = "작성자")
    private String memberId;

}
