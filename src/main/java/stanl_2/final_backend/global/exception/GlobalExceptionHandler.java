package stanl_2.final_backend.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
// 모든 rest컨트롤러에서 발생하는 예외 처리
@RestControllerAdvice(basePackages = "stanl_2.final_backend")
public class GlobalExceptionHandler {

    // 지원되지 않는 HTTP 메소드를 사용할 때 발생하는 예외
    @ExceptionHandler(value = {NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<GlobalExceptionResponse> handleNoPageFoundException(Exception e) {
        log.error("handleNoPageFoundException() in GlobalExceptionHandler throw NoHandlerFoundException : {}"
                , e.getMessage());
        GlobalExceptionResponse response = new GlobalExceptionResponse(new GlobalCommonException(GlobalErrorCode.WRONG_ENTRY_POINT).getErrorCode());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    // 메소드의 인자 타입이 일치하지 않을 때 발생하는 예외
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<GlobalExceptionResponse> handleArgumentNotValidException(MethodArgumentTypeMismatchException e) {
        log.error("handleArgumentNotValidException() in GlobalExceptionHandler throw MethodArgumentTypeMismatchException : {}"
                , e.getMessage());
        GlobalExceptionResponse response = new GlobalExceptionResponse(new GlobalCommonException(GlobalErrorCode.INVALID_PARAMETER_FORMAT).getErrorCode());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    // 필수 파라미터가 누락되었을 때 발생하는 예외
    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseEntity<GlobalExceptionResponse> handleArgumentNotValidException(MissingServletRequestParameterException e) {
        log.error("handleArgumentNotValidException() in GlobalExceptionHandler throw MethodArgumentNotValidException : {}"
                , e.getMessage());
        GlobalExceptionResponse response = new GlobalExceptionResponse(new GlobalCommonException(GlobalErrorCode.MISSING_REQUEST_PARAMETER).getErrorCode());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    // 사용자 정의 예외 처리
    @ExceptionHandler(value = {GlobalCommonException.class})
    public ResponseEntity<GlobalExceptionResponse> handleCustomException(GlobalCommonException e) {
        log.error("handleCustomException() in GlobalExceptionHandler: {}", e.getMessage());
        GlobalExceptionResponse response = new GlobalExceptionResponse(e.getErrorCode());

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    // 서버 내부 오류시 작동
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<GlobalExceptionResponse> handleServerException(Exception e) {
        log.info("occurred exception in handleServerError = {}", e.getMessage());
        e.printStackTrace();
        GlobalExceptionResponse response = new GlobalExceptionResponse(new GlobalCommonException(GlobalErrorCode.INTERNAL_SERVER_ERROR).getErrorCode());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    // 데이터 무결성 위반 예외 처리기 추가
    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<GlobalExceptionResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("handleDataIntegrityViolationException() in GlobalExceptionHandler : {}", e.getMessage());
        GlobalExceptionResponse response = new GlobalExceptionResponse(new GlobalCommonException(GlobalErrorCode.DATA_INTEGRITY_VIOLATION).getErrorCode());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<GlobalExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("유효성 검사 실패: {}", e.getMessage());
        GlobalExceptionResponse response = new GlobalExceptionResponse(new GlobalCommonException(GlobalErrorCode.VALIDATION_FAIL).getErrorCode());
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
