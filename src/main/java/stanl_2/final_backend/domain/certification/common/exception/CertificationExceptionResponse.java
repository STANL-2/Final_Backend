package stanl_2.final_backend.domain.certification.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CertificationExceptionResponse {
    private final Integer code;
    private final String msg;
    private final HttpStatus httpStatus;

    public CertificationExceptionResponse(CertificationErrorCode sampleErrorCode) {
        this.code = sampleErrorCode.getCode();
        this.msg = sampleErrorCode.getMsg();
        this.httpStatus = sampleErrorCode.getHttpStatus();
    }

    public static CertificationExceptionResponse of(CertificationErrorCode sampleErrorCode) {
        return new CertificationExceptionResponse(sampleErrorCode);
    }

}
