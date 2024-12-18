package stanl_2.final_backend.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GlobalExceptionResponse {
    private final Integer code;
    private final String msg;
    private final HttpStatus httpStatus;

    public GlobalExceptionResponse(GlobalErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
        this.httpStatus = errorCode.getHttpStatus();
    }

    public static GlobalExceptionResponse of(GlobalErrorCode errorCode) {
        return new GlobalExceptionResponse(errorCode);
    }

}
