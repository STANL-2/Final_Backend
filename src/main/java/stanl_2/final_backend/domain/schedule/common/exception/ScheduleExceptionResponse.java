package stanl_2.final_backend.domain.schedule.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ScheduleExceptionResponse {
    private final Integer code;
    private final String msg;
    private final HttpStatus httpStatus;

    public ScheduleExceptionResponse(ScheduleErrorCode scheduleErrorCode) {
        this.code = scheduleErrorCode.getCode();
        this.msg = scheduleErrorCode.getMsg();
        this.httpStatus = scheduleErrorCode.getHttpStatus();
    }

    public static ScheduleExceptionResponse of(ScheduleErrorCode scheduleErrorCode) {
        return new ScheduleExceptionResponse(scheduleErrorCode);
    }

}
