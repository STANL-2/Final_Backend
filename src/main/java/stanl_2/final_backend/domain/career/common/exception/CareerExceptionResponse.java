package stanl_2.final_backend.domain.career.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CareerExceptionResponse {
    private final Integer code;
    private final String msg;
    private final HttpStatus httpStatus;

    public CareerExceptionResponse(CareerErrorCode sampleErrorCode) {
        this.code = sampleErrorCode.getCode();
        this.msg = sampleErrorCode.getMsg();
        this.httpStatus = sampleErrorCode.getHttpStatus();
    }

    public static CareerExceptionResponse of(CareerErrorCode sampleErrorCode) {
        return new CareerExceptionResponse(sampleErrorCode);
    }

}
