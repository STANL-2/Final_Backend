package stanl_2.final_backend.domain.notices.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NoticeModifyDTO {
    private String title;

    private String tag;

    private String classification;

    private String content;
}
