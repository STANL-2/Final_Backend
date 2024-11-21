package stanl_2.final_backend.domain.product.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProductExceptionResponse {
    private final Integer code;
    private final String msg;
    private final HttpStatus httpStatus;

    public ProductExceptionResponse(ProductErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
        this.httpStatus = errorCode.getHttpStatus();
    }

    public static ProductExceptionResponse of(ProductErrorCode errorCode) {
        return new ProductExceptionResponse(errorCode);
    }

}
