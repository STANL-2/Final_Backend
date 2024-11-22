package stanl_2.final_backend.domain.s3.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class S3ExceptionResponse {
    private final Integer code;
    private final String msg;
    private final HttpStatus httpStatus;

    public S3ExceptionResponse(S3ErrorCode sampleErrorCode) {
        this.code = sampleErrorCode.getCode();
        this.msg = sampleErrorCode.getMsg();
        this.httpStatus = sampleErrorCode.getHttpStatus();
    }

    public static S3ExceptionResponse of(S3ErrorCode sampleErrorCode) {
        return new S3ExceptionResponse(sampleErrorCode);
    }

}
