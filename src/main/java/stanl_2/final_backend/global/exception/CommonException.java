package stanl_2.final_backend.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@RequiredArgsConstructor
public class CommonException extends RuntimeException {
    private final ErrorCode errorCode;

    // 에러 발생시 ErroCode 별 메시지
    @Override
    public String getMessage() {
        return this.errorCode.getMsg();
    }
}
