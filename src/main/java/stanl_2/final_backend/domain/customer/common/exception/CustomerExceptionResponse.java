package stanl_2.final_backend.domain.customer.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomerExceptionResponse {
    private final Integer code;
    private final String msg;
    private final HttpStatus httpStatus;

    public CustomerExceptionResponse(CustomerErrorCode sampleErrorCode) {
        this.code = sampleErrorCode.getCode();
        this.msg = sampleErrorCode.getMsg();
        this.httpStatus = sampleErrorCode.getHttpStatus();
    }

    public static CustomerExceptionResponse of(CustomerErrorCode sampleErrorCode) {
        return new CustomerExceptionResponse(sampleErrorCode);
    }

}
