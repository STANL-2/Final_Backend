package stanl_2.final_backend.domain.purchase_order.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PurchaseOrderExceptionResponse {
    private final Integer code;
    private final String msg;
    private final HttpStatus httpStatus;

    public PurchaseOrderExceptionResponse(PurchaseOrderErrorCode sampleErrorCode) {
        this.code = sampleErrorCode.getCode();
        this.msg = sampleErrorCode.getMsg();
        this.httpStatus = sampleErrorCode.getHttpStatus();
    }

    public static PurchaseOrderExceptionResponse of(PurchaseOrderErrorCode sampleErrorCode) {
        return new PurchaseOrderExceptionResponse(sampleErrorCode);
    }

}
