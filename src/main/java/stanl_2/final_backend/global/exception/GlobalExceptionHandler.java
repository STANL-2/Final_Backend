package stanl_2.final_backend.global.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import stanl_2.final_backend.domain.log.command.aggregate.Log;
import stanl_2.final_backend.domain.log.command.repository.LogRepository;
import stanl_2.final_backend.global.log.LoggingFilter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
// 모든 rest컨트롤러에서 발생하는 예외 처리
@RestControllerAdvice(basePackages = "stanl_2.final_backend")
public class GlobalExceptionHandler {

    @Autowired
    private LogRepository logRepository;  // 로그를 저장하기 위한 리포지토리 주입

    private void saveErrorLog(String message, Exception e) {
        String transactionId = LoggingFilter.getCurrentTransactionId(); // ThreadLocal에서 가져옴
        if (transactionId == null) {
            transactionId = UUID.randomUUID().toString(); // 누락 시 새로운 ID 생성
        }
//        Log log = new Log();
//        log.setTransactionId(transactionId);
//        log.setStatus("ERROR");
//        log.setErrorMessage(e.getMessage());
//        logRepository.save(log);

        try {
            Log log = new Log();
            log.setStatus(message);
            log.setErrorMessage(e.getMessage());
            log.setRequestTime(LocalDateTime.now());
            logRepository.save(log); // 로그 저장
        } catch (Exception ex) {
            log.error("로그 저장 중 오류 발생: {}", ex.getMessage(), ex);
        }
    }

//    // 지원되지 않는 HTTP 메소드를 사용할 때 발생하는 예외
//    @ExceptionHandler(value = {NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
//    public ResponseEntity<GlobalExceptionResponse> handleNoPageFoundException(Exception e) {
//        log.error("지원되지 않는 HTTP 메소드 요청: {}", e.getMessage());
//        saveErrorLog(GlobalErrorCode.WRONG_ENTRY_POINT.getMsg(), e);
//        GlobalExceptionResponse response = new GlobalExceptionResponse(new GlobalCommonException(GlobalErrorCode.WRONG_ENTRY_POINT).getErrorCode());
////        loggingAspect.logRequestFailure(GlobalErrorCode.WRONG_ENTRY_POINT.getMsg());
//        return ResponseEntity.status(response.getHttpStatus()).body(response);
//    }
//
//    // 메소드의 인자 타입이 일치하지 않을 때 발생하는 예외
//    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
//    public ResponseEntity<GlobalExceptionResponse> handleArgumentNotValidException(MethodArgumentTypeMismatchException e) {
//        log.error("메소드 인자 타입 불일치: {}"
//                , e.getMessage());
//        saveErrorLog(GlobalErrorCode.INVALID_PARAMETER_FORMAT.getMsg(), e);
//        GlobalExceptionResponse response = new GlobalExceptionResponse(new GlobalCommonException(GlobalErrorCode.INVALID_PARAMETER_FORMAT).getErrorCode());
////        loggingAspect.logRequestFailure(GlobalErrorCode.INVALID_PARAMETER_FORMAT.getMsg());
//        return ResponseEntity.status(response.getHttpStatus()).body(response);
//    }
//
//    // 필수 파라미터가 누락되었을 때 발생하는 예외
//    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
//    public ResponseEntity<GlobalExceptionResponse> handleArgumentNotValidException(MissingServletRequestParameterException e) {
//        log.error("필수 파라미터 누락: {}"
//                , e.getMessage());
//        saveErrorLog(GlobalErrorCode.MISSING_REQUEST_PARAMETER.getMsg(), e);
//        GlobalExceptionResponse response = new GlobalExceptionResponse(new GlobalCommonException(GlobalErrorCode.MISSING_REQUEST_PARAMETER).getErrorCode());
////        loggingAspect.logRequestFailure(GlobalErrorCode.MISSING_REQUEST_PARAMETER.getMsg());
//        return ResponseEntity.status(response.getHttpStatus()).body(response);
//    }
//
//    // 사용자 정의 예외 처리
//    @ExceptionHandler(value = {GlobalCommonException.class})
//    public ResponseEntity<Map<String, Object>> handleCustomException(GlobalCommonException e) {
//        log.error("사용자 예외처리: {}", e.getMessage());
//        saveErrorLog(e.getErrorCode().getMsg(), e);
////        GlobalExceptionResponse response = new GlobalExceptionResponse(e.getErrorCode());
////        loggingAspect.logRequestFailure(e.getErrorCode().getMsg());
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("message", e.getErrorCode().getMsg());
//        response.put("code", e.getErrorCode().getCode());
//        return new ResponseEntity<>(response, e.getErrorCode().getHttpStatus());
//
////        return ResponseEntity.status(response.getHttpStatus()).body(response);
//    }
////
////    @ExceptionHandler(GlobalCommonException.class)
////    public ResponseEntity<Map<String, Object>> handleGlobalCommonException(GlobalCommonException ex) {
////        // 로그 저장
////        log.error("Error occurred: {}", ex.getErrorCode());
////        Map<String, Object> response = new HashMap<>();
////        response.put("message", ex.getErrorCode().getMsg());
////        response.put("code", ex.getErrorCode().getCode());
////        return new ResponseEntity<>(response, ex.getErrorCode().getHttpStatus());
////    }
//
//    // 서버 내부 오류시 작동
//    @ExceptionHandler(value = {Exception.class})
//    public ResponseEntity<GlobalExceptionResponse> handleServerException(Exception e) {
//        log.info("서버 내부 오류 발생: {}", e.getMessage());
//        e.printStackTrace();
//        saveErrorLog(GlobalErrorCode.INTERNAL_SERVER_ERROR.getMsg(), e);
//        GlobalExceptionResponse response = new GlobalExceptionResponse(new GlobalCommonException(GlobalErrorCode.INTERNAL_SERVER_ERROR).getErrorCode());
////        loggingAspect.logRequestFailure(GlobalErrorCode.INTERNAL_SERVER_ERROR.getMsg());
//        return ResponseEntity.status(response.getHttpStatus()).body(response);
//    }
//
//    // 데이터 무결성 위반 예외 처리기 추가
//    @ExceptionHandler(value = {DataIntegrityViolationException.class})
//    public ResponseEntity<GlobalExceptionResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
//        log.error("데이터 무결성 위반: {}", e.getMessage());
//        saveErrorLog(GlobalErrorCode.DATA_INTEGRITY_VIOLATION.getMsg(), e);
//        GlobalExceptionResponse response = new GlobalExceptionResponse(new GlobalCommonException(GlobalErrorCode.DATA_INTEGRITY_VIOLATION).getErrorCode());
////        loggingAspect.logRequestFailure(GlobalErrorCode.DATA_INTEGRITY_VIOLATION.getMsg());
//        return ResponseEntity.status(response.getHttpStatus()).body(response);
//    }
//
//    // 유효성 검사 실패 예외
//    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
//    public ResponseEntity<GlobalExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//        log.error("유효성 검사 실패: {}", e.getMessage());
//        saveErrorLog(GlobalErrorCode.VALIDATION_FAIL.getMsg(), e);
//        GlobalExceptionResponse response = new GlobalExceptionResponse(new GlobalCommonException(GlobalErrorCode.VALIDATION_FAIL).getErrorCode());
////        loggingAspect.logRequestFailure(GlobalErrorCode.VALIDATION_FAIL.getMsg());
//        return ResponseEntity.status(response.getHttpStatus()).body(response);
//    }
//
//    // JWT 토큰 만료 예외 처리
//    @ExceptionHandler(ExpiredJwtException.class)
//    public ResponseEntity<GlobalExceptionResponse> handleExpiredJwtException(ExpiredJwtException e) {
//        log.error("만료된 JWT 토큰: {}", e.getMessage());
//        saveErrorLog(GlobalErrorCode.JWT_EXPIRED.getMsg(), e);
//        GlobalExceptionResponse response = new GlobalExceptionResponse(new GlobalCommonException(GlobalErrorCode.JWT_EXPIRED).getErrorCode());
////        loggingAspect.logRequestFailure(GlobalErrorCode.JWT_EXPIRED.getMsg());
//        return ResponseEntity.status(response.getHttpStatus()).body(response);
//    }
//
//    // JWT 토큰 인증 실패 예외 처리
//    @ExceptionHandler(JwtException.class)
//    public ResponseEntity<GlobalExceptionResponse> handleJwtException(JwtException e) {
//        log.error("유효하지 않은 JWT 토큰: {}", e.getMessage());
//        saveErrorLog(GlobalErrorCode.INVALID_TOKEN_ERROR.getMsg(), e);
//        GlobalExceptionResponse response = new GlobalExceptionResponse(new GlobalCommonException(GlobalErrorCode.INVALID_TOKEN_ERROR).getErrorCode());
////        loggingAspect.logRequestFailure(GlobalErrorCode.INVALID_TOKEN_ERROR.getMsg());
//        return ResponseEntity.status(response.getHttpStatus()).body(response);
//    }

    private ResponseEntity<GlobalExceptionResponse> createErrorResponse(GlobalErrorCode errorCode, Exception e) {
        saveErrorLog(errorCode.getMsg(), e);
        GlobalExceptionResponse response = new GlobalExceptionResponse(new GlobalCommonException(errorCode).getErrorCode());
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @ExceptionHandler({NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<GlobalExceptionResponse> handleNoPageFoundException(Exception e) {
        log.error("지원되지 않는 요청: {}", e.getMessage());
        return createErrorResponse(GlobalErrorCode.WRONG_ENTRY_POINT, e);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<GlobalExceptionResponse> handleArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("인자 타입 불일치: {}", e.getMessage());
        return createErrorResponse(GlobalErrorCode.INVALID_PARAMETER_FORMAT, e);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<GlobalExceptionResponse> handleMissingRequestParameterException(MissingServletRequestParameterException e) {
        log.error("필수 파라미터 누락: {}", e.getMessage());
        return createErrorResponse(GlobalErrorCode.MISSING_REQUEST_PARAMETER, e);
    }

    @ExceptionHandler(GlobalCommonException.class)
    public ResponseEntity<Map<String, Object>> handleCustomException(GlobalCommonException e) {
        log.error("사용자 예외 처리: {}", e.getMessage());
        saveErrorLog(e.getErrorCode().getMsg(), e);
        Map<String, Object> response = new HashMap<>();
        response.put("message", e.getErrorCode().getMsg());
        response.put("code", e.getErrorCode().getCode());
        return new ResponseEntity<>(response, e.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalExceptionResponse> handleServerException(Exception e) {
        log.error("서버 내부 오류 발생: {}", e.getMessage());
        return createErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR, e);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<GlobalExceptionResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("데이터 무결성 위반: {}", e.getMessage());
        return createErrorResponse(GlobalErrorCode.DATA_INTEGRITY_VIOLATION, e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("유효성 검사 실패: {}", e.getMessage());
        return createErrorResponse(GlobalErrorCode.VALIDATION_FAIL, e);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<GlobalExceptionResponse> handleExpiredJwtException(ExpiredJwtException e) {
        log.error("만료된 JWT 토큰: {}", e.getMessage());
        return createErrorResponse(GlobalErrorCode.JWT_EXPIRED, e);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<GlobalExceptionResponse> handleJwtException(JwtException e) {
        log.error("유효하지 않은 JWT 토큰: {}", e.getMessage());
        return createErrorResponse(GlobalErrorCode.INVALID_TOKEN_ERROR, e);
    }

}
