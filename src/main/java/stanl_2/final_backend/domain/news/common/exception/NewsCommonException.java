package stanl_2.final_backend.domain.news.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NewsCommonException extends RuntimeException {
    private final NewsErrorCode errorCode;

    // 에러 발생시 ErroCode 별 메시지
    @Override
    public String getMessage() {
        return this.errorCode.getMsg();
    }
}
