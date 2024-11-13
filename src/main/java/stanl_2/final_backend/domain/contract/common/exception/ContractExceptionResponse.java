package stanl_2.final_backend.domain.contract.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ContractExceptionResponse {
    private final Integer code;
    private final String msg;
    private final HttpStatus httpStatus;

    public ContractExceptionResponse(ContractErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
        this.httpStatus = errorCode.getHttpStatus();
    }

    public static ContractExceptionResponse of(ContractErrorCode errorCode) {
        return new ContractExceptionResponse(errorCode);
    }

}
