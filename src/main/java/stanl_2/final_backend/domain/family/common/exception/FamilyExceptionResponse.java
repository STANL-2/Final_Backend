package stanl_2.final_backend.domain.family.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FamilyExceptionResponse {
    private final Integer code;
    private final String msg;
    private final HttpStatus httpStatus;

    public FamilyExceptionResponse(FamilyErrorCode sampleErrorCode) {
        this.code = sampleErrorCode.getCode();
        this.msg = sampleErrorCode.getMsg();
        this.httpStatus = sampleErrorCode.getHttpStatus();
    }

    public static FamilyExceptionResponse of(FamilyErrorCode sampleErrorCode) {
        return new FamilyExceptionResponse(sampleErrorCode);
    }

}
