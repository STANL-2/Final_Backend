package stanl_2.final_backend.domain.evaluation.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EvaluationSearchDTO {
    private String evaluationId;
    private String title;
    private String writerName;
    private String memberName;
    private String centerId;
    private String startDate;
    private String endDate;
    private String searcherName;
    private String fileUrl;
}
