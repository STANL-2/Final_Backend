package stanl_2.final_backend.domain.notices.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import stanl_2.final_backend.domain.A_sample.common.exception.SampleErrorCode;

@Getter
@RequiredArgsConstructor
public class NoticeCommonException extends RuntimeException {
    private final NoticeErrorCode noticeErrorCode;

    // 에러 발생시 ErroCode 별 메시지
    @Override
    public String getMessage() {
        return this.noticeErrorCode.getMsg();
    }
}
