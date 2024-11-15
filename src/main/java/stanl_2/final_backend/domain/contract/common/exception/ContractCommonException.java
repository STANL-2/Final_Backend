package stanl_2.final_backend.domain.contract.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ContractCommonException extends RuntimeException {
    private final ContractErrorCode errorCode;

    // 에러 발생시 ErroCode 별 메시지
    @Override
    public String getMessage() {
        return this.errorCode.getMsg();
    }
}
