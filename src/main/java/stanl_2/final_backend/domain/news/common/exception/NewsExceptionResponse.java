package stanl_2.final_backend.domain.news.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NewsExceptionResponse {
    private final Integer code;
    private final String msg;
    private final HttpStatus httpStatus;

    public NewsExceptionResponse(NewsErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
        this.httpStatus = errorCode.getHttpStatus();
    }

    public static NewsExceptionResponse of(NewsErrorCode errorCode) {
        return new NewsExceptionResponse(errorCode);
    }

}
