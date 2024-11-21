package stanl_2.final_backend.domain.center.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CenterCommonException extends RuntimeException {
    private final CenterErrorCode centerErrorCode;

    // 에러 발생시 ErroCode 별 메시지
    @Override
    public String getMessage() {
        return this.centerErrorCode.getMsg();
    }
}
