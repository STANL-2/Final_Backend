package stanl_2.final_backend.domain.A_sample.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SampleExceptionResponse {
    private final Integer code;
    private final String msg;
    private final HttpStatus httpStatus;

    public SampleExceptionResponse(SampleErrorCode sampleErrorCode) {
        this.code = sampleErrorCode.getCode();
        this.msg = sampleErrorCode.getMsg();
        this.httpStatus = sampleErrorCode.getHttpStatus();
    }

    public static SampleExceptionResponse of(SampleErrorCode sampleErrorCode) {
        return new SampleExceptionResponse(sampleErrorCode);
    }

}
