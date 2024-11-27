package stanl_2.final_backend.domain.center.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CenterExceptionResponse {
    private final Integer code;
    private final String msg;
    private final HttpStatus httpStatus;

    public CenterExceptionResponse(CenterErrorCode centerErrorCode) {
        this.code = centerErrorCode.getCode();
        this.msg = centerErrorCode.getMsg();
        this.httpStatus = centerErrorCode.getHttpStatus();
    }

    public static CenterExceptionResponse of(CenterErrorCode centerErrorCode) {
        return new CenterExceptionResponse(centerErrorCode);
    }

}
