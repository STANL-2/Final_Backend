package stanl_2.final_backend.domain.evaluation.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EvaluationDTO {
    private String evalId;
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private Boolean active;
    private String centerId;
    private String memberId;
    private String writerId;
}
