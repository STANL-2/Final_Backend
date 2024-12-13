package stanl_2.final_backend.domain.schedule.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ScheduleCommonException extends RuntimeException {
    private final ScheduleErrorCode scheduleErrorCode;

    // 에러 발생시 ErroCode 별 메시지
    @Override
    public String getMessage() {
        return this.scheduleErrorCode.getMsg();
    }
}
