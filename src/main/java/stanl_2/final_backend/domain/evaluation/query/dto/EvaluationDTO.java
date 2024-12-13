package stanl_2.final_backend.domain.evaluation.query.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EvaluationDTO {
    private String evaluationId;
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;
    private String centerId;
    private String memberId;
    private String writerId;
    private String fileUrl;
}
