package stanl_2.final_backend.domain.promotion.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PromotionCommonException extends RuntimeException {
    private final PromotionErrorCode promotionErrorCode;

    // 에러 발생시 ErroCode 별 메시지
    @Override
    public String getMessage() {
        return this.promotionErrorCode.getMsg();
    }
}
