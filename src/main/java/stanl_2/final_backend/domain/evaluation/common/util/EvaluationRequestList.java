package stanl_2.final_backend.domain.evaluation.common.util;

import lombok.*;
import org.springframework.data.domain.Pageable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationRequestList<T> {
    private T data;
    private Pageable pageable;
}
