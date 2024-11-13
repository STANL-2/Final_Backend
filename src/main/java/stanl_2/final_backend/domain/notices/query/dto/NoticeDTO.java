package stanl_2.final_backend.domain.notices.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stanl_2.final_backend.domain.notices.command.domain.aggragate.entity.Notice;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NoticeDTO {
    private String noticeId;

    private String title;

    private String tag;

    private String classification;

    private String content;

    private String createdAt;

    private String updatedAt;

    private String deletedAt;

    private Boolean active;

    private String memberId;


    public NoticeDTO(Notice notice) {
        this.noticeId = notice.getNoticeId();
        this.title = notice.getTitle();
        this.tag = notice.getTag();
        this.classification = notice.getClassification();
        this.content = notice.getContent();
        this.createdAt = notice.getCreatedAt();
        this.updatedAt = notice.getUpdatedAt();
        this.deletedAt = notice.getDeletedAt();
        this.active = notice.getActive();
        this.memberId = notice.getMemberId();
    }
}
