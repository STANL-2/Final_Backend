package stanl_2.final_backend.domain.log.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LogExceptionResponse {
    private final Integer code;
    private final String msg;
    private final HttpStatus httpStatus;

    public LogExceptionResponse(LogErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
        this.httpStatus = errorCode.getHttpStatus();
    }

    public static LogExceptionResponse of(LogErrorCode errorCode) {
        return new LogExceptionResponse(errorCode);
    }

}
