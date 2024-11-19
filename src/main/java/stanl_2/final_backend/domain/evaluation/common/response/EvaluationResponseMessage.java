package stanl_2.final_backend.domain.evaluation.common.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class EvaluationResponseMessage {
    private int httpStatus;
    private String msg;
    private Object result;
}
