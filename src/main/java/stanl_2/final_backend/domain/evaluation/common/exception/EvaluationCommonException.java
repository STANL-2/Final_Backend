package stanl_2.final_backend.domain.evaluation.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import stanl_2.final_backend.domain.A_sample.common.exception.SampleErrorCode;

@Getter
@RequiredArgsConstructor
public class EvaluationCommonException extends RuntimeException {
    private final EvaluationErrorCode evaluationErrorCode;

    // 에러 발생시 ErroCode 별 메시지
    @Override
    public String getMessage() {
        return this.evaluationErrorCode.getMsg();
    }
}
