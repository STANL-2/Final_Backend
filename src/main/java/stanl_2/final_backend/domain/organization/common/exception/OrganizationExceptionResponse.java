package stanl_2.final_backend.domain.organization.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import stanl_2.final_backend.domain.A_sample.common.exception.SampleErrorCode;

@Getter
public class OrganizationExceptionResponse {
    private final Integer code;
    private final String msg;
    private final HttpStatus httpStatus;

    public OrganizationExceptionResponse(stanl_2.final_backend.domain.A_sample.common.exception.SampleErrorCode sampleErrorCode) {
        this.code = sampleErrorCode.getCode();
        this.msg = sampleErrorCode.getMsg();
        this.httpStatus = sampleErrorCode.getHttpStatus();
    }

    public static OrganizationExceptionResponse of(SampleErrorCode sampleErrorCode) {
        return new OrganizationExceptionResponse(sampleErrorCode);
    }

}
