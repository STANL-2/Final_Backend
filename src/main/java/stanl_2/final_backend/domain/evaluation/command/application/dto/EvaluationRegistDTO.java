package stanl_2.final_backend.domain.evaluation.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EvaluationRegistDTO {
    private String title;
    private String content;
    private String memberId;
    private String writerId;
    private String centerId;
    private String fileUrl;
}
