package stanl_2.final_backend.domain.problem.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProblemModifyDTO {

    private String title;
    private String content;
    private String memberId;
    private String fileUrl;

}
