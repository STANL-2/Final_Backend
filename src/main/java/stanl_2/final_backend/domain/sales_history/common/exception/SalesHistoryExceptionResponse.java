package stanl_2.final_backend.domain.sales_history.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SalesHistoryExceptionResponse {
    private final Integer code;
    private final String msg;
    private final HttpStatus httpStatus;

    public SalesHistoryExceptionResponse(SalesHistoryErrorCode salesHistoryErrorCode) {
        this.code = salesHistoryErrorCode.getCode();
        this.msg = salesHistoryErrorCode.getMsg();
        this.httpStatus = salesHistoryErrorCode.getHttpStatus();
    }

    public static SalesHistoryExceptionResponse of(SalesHistoryErrorCode salesHistoryErrorCode) {
        return new SalesHistoryExceptionResponse(salesHistoryErrorCode);
    }

}
