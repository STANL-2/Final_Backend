package stanl_2.final_backend.domain.member.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MemberExceptionResponse {
    private final Integer code;
    private final String msg;
    private final HttpStatus httpStatus;

    public MemberExceptionResponse(MemberErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
        this.httpStatus = errorCode.getHttpStatus();
    }

    public static MemberExceptionResponse of(MemberErrorCode errorCode) {
        return new MemberExceptionResponse(errorCode);
    }

}
