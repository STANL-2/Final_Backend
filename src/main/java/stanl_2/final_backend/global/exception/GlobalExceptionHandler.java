package stanl_2.final_backend.global.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
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
        log.error("지원되지 않는 HTTP 메소드 요청: {}", e.getMessage());
        GlobalExceptionResponse response = new GlobalExceptionResponse(new GlobalCommonException(GlobalErrorCode.WRONG_ENTRY_POINT).getErrorCode());
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    // 메소드의 인자 타입이 일치하지 않을 때 발생하는 예외
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<GlobalExceptionResponse> handleArgumentNotValidException(MethodArgumentTypeMismatchException e) {
        log.error("메소드 인자 타입 불일치: {}"
                , e.getMessage());
        GlobalExceptionResponse response = new GlobalExceptionResponse(new GlobalCommonException(GlobalErrorCode.INVALID_PARAMETER_FORMAT).getErrorCode());
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    // 필수 파라미터가 누락되었을 때 발생하는 예외
    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseEntity<GlobalExceptionResponse> handleArgumentNotValidException(MissingServletRequestParameterException e) {
        log.error("필수 파라미터 누락: {}"
                , e.getMessage());
        GlobalExceptionResponse response = new GlobalExceptionResponse(new GlobalCommonException(GlobalErrorCode.MISSING_REQUEST_PARAMETER).getErrorCode());
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    // 사용자 정의 예외 처리
    @ExceptionHandler(value = {GlobalCommonException.class})
    public ResponseEntity<GlobalExceptionResponse> handleCustomException(GlobalCommonException e) {
        log.error("사용자 예외처리: {}", e.getMessage());
        GlobalExceptionResponse response = new GlobalExceptionResponse(e.getErrorCode());

        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    // 서버 내부 오류시 작동
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<GlobalExceptionResponse> handleServerException(Exception e) {
        log.info("서버 내부 오류 발생: {}", e.getMessage());
        e.printStackTrace();
        GlobalExceptionResponse response = new GlobalExceptionResponse(new GlobalCommonException(GlobalErrorCode.INTERNAL_SERVER_ERROR).getErrorCode());
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    // 데이터 무결성 위반 예외 처리기 추가
    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<GlobalExceptionResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("데이터 무결성 위반: {}", e.getMessage());
        GlobalExceptionResponse response = new GlobalExceptionResponse(new GlobalCommonException(GlobalErrorCode.DATA_INTEGRITY_VIOLATION).getErrorCode());
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    // 유효성 검사 실패 예외
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<GlobalExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("유효성 검사 실패: {}", e.getMessage());
        GlobalExceptionResponse response = new GlobalExceptionResponse(new GlobalCommonException(GlobalErrorCode.VALIDATION_FAIL).getErrorCode());
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    // JWT 토큰 만료 예외 처리
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<GlobalExceptionResponse> handleExpiredJwtException(ExpiredJwtException e) {
        log.error("만료된 JWT 토큰: {}", e.getMessage());
        GlobalExceptionResponse response = new GlobalExceptionResponse(new GlobalCommonException(GlobalErrorCode.JWT_EXPIRED).getErrorCode());
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    // JWT 토큰 인증 실패 예외 처리
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<GlobalExceptionResponse> handleJwtException(JwtException e) {
        log.error("유효하지 않은 JWT 토큰: {}", e.getMessage());
        GlobalExceptionResponse response = new GlobalExceptionResponse(new GlobalCommonException(GlobalErrorCode.INVALID_TOKEN_ERROR).getErrorCode());
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

}
