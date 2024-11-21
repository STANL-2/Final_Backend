package stanl_2.final_backend.domain.alarm.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AlarmCommonException extends RuntimeException {
    private final AlarmErrorCode alarmErrorCode;

    // 에러 발생시 ErroCode 별 메시지
    @Override
    public String getMessage() {
        return this.alarmErrorCode.getMsg();
    }
}
