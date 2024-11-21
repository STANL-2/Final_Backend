package stanl_2.final_backend.domain.alarm.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AlarmExceptionResponse {
    private final Integer code;
    private final String msg;
    private final HttpStatus httpStatus;

    public AlarmExceptionResponse(AlarmErrorCode alarmErrorCode) {
        this.code = alarmErrorCode.getCode();
        this.msg = alarmErrorCode.getMsg();
        this.httpStatus = alarmErrorCode.getHttpStatus();
    }

    public static AlarmExceptionResponse of(AlarmErrorCode alarmErrorCode) {
        return new AlarmExceptionResponse(alarmErrorCode);
    }

}
