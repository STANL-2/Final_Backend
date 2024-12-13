package stanl_2.final_backend.domain.notices.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import stanl_2.final_backend.domain.notices.command.domain.aggragate.entity.Notice;
import stanl_2.final_backend.domain.notices.query.repository.NoticeMapper;

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

    private String fileUrl;
}
