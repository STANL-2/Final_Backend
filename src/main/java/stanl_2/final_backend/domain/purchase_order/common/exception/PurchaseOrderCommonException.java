package stanl_2.final_backend.domain.purchase_order.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PurchaseOrderCommonException extends RuntimeException {
    private final PurchaseOrderErrorCode sampleErrorCode;

    // 에러 발생시 ErroCode 별 메시지
    @Override
    public String getMessage() {
        return this.sampleErrorCode.getMsg();
    }
}
