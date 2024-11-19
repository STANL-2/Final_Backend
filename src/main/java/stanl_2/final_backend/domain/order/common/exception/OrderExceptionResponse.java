package stanl_2.final_backend.domain.order.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OrderExceptionResponse {
    private final Integer code;
    private final String msg;
    private final HttpStatus httpStatus;

    public OrderExceptionResponse(OrderErrorCode sampleErrorCode) {
        this.code = sampleErrorCode.getCode();
        this.msg = sampleErrorCode.getMsg();
        this.httpStatus = sampleErrorCode.getHttpStatus();
    }

    public static OrderExceptionResponse of(OrderErrorCode sampleErrorCode) {
        return new OrderExceptionResponse(sampleErrorCode);
    }

}
