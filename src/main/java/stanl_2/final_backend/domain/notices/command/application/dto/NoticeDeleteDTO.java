package stanl_2.final_backend.domain.notices.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NoticeDeleteDTO {
    private String NoticeId;
    private String memberLoginId;
}
