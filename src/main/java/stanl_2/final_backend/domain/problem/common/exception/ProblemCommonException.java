package stanl_2.final_backend.domain.problem.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProblemCommonException extends RuntimeException {
    private final ProblemErrorCode problemErrorCode;

    // 에러 발생시 ErroCode 별 메시지
    @Override
    public String getMessage() {
        return this.problemErrorCode.getMsg();
    }
}
