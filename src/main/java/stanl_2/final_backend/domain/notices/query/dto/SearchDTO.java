package stanl_2.final_backend.domain.notices.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SearchDTO {
    private String noticeId;
    private String title;
    private String tag;
    private String content;
    private String active;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private String memberId;
    private String classification;
    private String startDate;
    private String endDate;

    public SearchDTO(String title, String tag, String memberId, String classification, String startDate, String endDate) {
        this.title = title;
        this.tag = tag;
        this.memberId = memberId;
        this.classification = classification;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
