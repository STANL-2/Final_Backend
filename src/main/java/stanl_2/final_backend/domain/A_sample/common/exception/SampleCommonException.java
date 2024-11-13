package stanl_2.final_backend.domain.A_sample.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SampleCommonException extends RuntimeException {
    private final SampleErrorCode sampleErrorCode;

    // 에러 발생시 ErroCode 별 메시지
    @Override
    public String getMessage() {
        return this.sampleErrorCode.getMsg();
    }
}
