package stanl_2.final_backend.domain.dashBoard.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DashBoardExceptionResponse {
    private final Integer code;
    private final String msg;
    private final HttpStatus httpStatus;

    public DashBoardExceptionResponse(DashBoardErrorCode sampleErrorCode) {
        this.code = sampleErrorCode.getCode();
        this.msg = sampleErrorCode.getMsg();
        this.httpStatus = sampleErrorCode.getHttpStatus();
    }

    public static DashBoardExceptionResponse of(DashBoardErrorCode sampleErrorCode) {
        return new DashBoardExceptionResponse(sampleErrorCode);
    }

}
