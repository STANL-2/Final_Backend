package stanl_2.final_backend.domain.notices.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NoticeModifyDTO {
    private String noticeId;
    private String title;
    private String tag;
    private String memberId;
    private String classification;
    private String content;
    private String memberLoginId;
    private String fileUrl;
}
