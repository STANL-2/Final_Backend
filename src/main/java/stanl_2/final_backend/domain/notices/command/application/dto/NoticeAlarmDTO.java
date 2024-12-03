package stanl_2.final_backend.domain.notices.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NoticeAlarmDTO {

    private String noticeId;

    private String title;

    private String tag;

    private String classification;

    private String content;

    private String memberLoginId;

    private String memberId;

    private String adminId;
}
