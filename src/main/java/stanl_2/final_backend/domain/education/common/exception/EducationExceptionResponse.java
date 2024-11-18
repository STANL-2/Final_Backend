package stanl_2.final_backend.domain.education.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EducationExceptionResponse {
    private final Integer code;
    private final String msg;
    private final HttpStatus httpStatus;

    public EducationExceptionResponse(EducationErrorCode sampleErrorCode) {
        this.code = sampleErrorCode.getCode();
        this.msg = sampleErrorCode.getMsg();
        this.httpStatus = sampleErrorCode.getHttpStatus();
    }

    public static EducationExceptionResponse of(EducationErrorCode sampleErrorCode) {
        return new EducationExceptionResponse(sampleErrorCode);
    }

}
