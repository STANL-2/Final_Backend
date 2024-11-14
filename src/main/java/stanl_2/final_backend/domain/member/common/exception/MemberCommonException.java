package stanl_2.final_backend.domain.member.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberCommonException extends RuntimeException {
    private final MemberErrorCode errorCode;

    // 에러 발생시 ErroCode 별 메시지
    @Override
    public String getMessage() {
        return this.errorCode.getMsg();
    }
}
