package stanl_2.final_backend.domain.evaluation.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class EvaluationModifyDTO {
    private String title;
    private String content;
    private String centerId;
    private String memberId;
    private String writerId;
}
