package stanl_2.final_backend.domain.problem.common.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProblemResponseMessage {
    private int httpStatus;
    private String msg;
    private Object result;
}
