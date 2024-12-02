package stanl_2.final_backend.domain.problem.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Component
public class ProblemModifyDTO {

    private String title;
    private String content;
    private String memberId;
    private String fileUrl;

}
