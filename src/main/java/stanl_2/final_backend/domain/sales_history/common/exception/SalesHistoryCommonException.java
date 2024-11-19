package stanl_2.final_backend.domain.sales_history.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SalesHistoryCommonException extends RuntimeException {
    private final SalesHistoryErrorCode salesHistoryErrorCode;

    // 에러 발생시 ErroCode 별 메시지
    @Override
    public String getMessage() {
        return this.salesHistoryErrorCode.getMsg();
    }
}
