package stanl_2.final_backend.domain.dashBoard.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DashBoardCommonException extends RuntimeException {
    private final DashBoardErrorCode sampleErrorCode;

    // 에러 발생시 ErroCode 별 메시지
    @Override
    public String getMessage() {
        return this.sampleErrorCode.getMsg();
    }
}
