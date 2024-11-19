package stanl_2.final_backend.domain.evaluation.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import stanl_2.final_backend.domain.A_sample.common.exception.SampleErrorCode;

@Getter
public class EvaluationExceptionResponse {
    private final Integer code;
    private final String msg;
    private final HttpStatus httpStatus;

    public EvaluationExceptionResponse(EvaluationErrorCode evaluationErrorCode) {
        this.code = evaluationErrorCode.getCode();
        this.msg = evaluationErrorCode.getMsg();
        this.httpStatus = evaluationErrorCode.getHttpStatus();
    }

    public static EvaluationExceptionResponse of(EvaluationErrorCode evaluationErrorCode) {
        return new EvaluationExceptionResponse(evaluationErrorCode);
    }

}
