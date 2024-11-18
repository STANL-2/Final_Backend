package stanl_2.final_backend.domain.career.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CareerCommonException extends RuntimeException {
    private final CareerErrorCode sampleErrorCode;

    // 에러 발생시 ErroCode 별 메시지
    @Override
    public String getMessage() {
        return this.sampleErrorCode.getMsg();
    }
}
