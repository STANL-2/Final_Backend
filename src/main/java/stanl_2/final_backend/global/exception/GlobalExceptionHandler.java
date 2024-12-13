package stanl_2.final_backend.global.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import stanl_2.final_backend.domain.log.command.aggregate.Log;
import stanl_2.final_backend.domain.log.command.repository.LogRepository;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestControllerAdvice(basePackages = "stanl_2.final_backend")
public class GlobalExceptionHandler {

    private final LogRepository logRepository;
    private final MemberQueryService memberQueryService;

    @Autowired
    public GlobalExceptionHandler(LogRepository logRepository,
                                  MemberQueryService memberQueryService) {
        this.logRepository = logRepository;
        this.memberQueryService = memberQueryService;
    }

    @ExceptionHandler({NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<GlobalExceptionResponse> handleNoPageFoundException(Exception e, HttpServletRequest request) {
        log.error("지원되지 않는 요청: {}", e.getMessage());
        return createErrorResponse(GlobalErrorCode.WRONG_ENTRY_POINT, e, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<GlobalExceptionResponse> handleArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        log.error("인자 타입 불일치: {}", e.getMessage());
        return createErrorResponse(GlobalErrorCode.INVALID_PARAMETER_FORMAT, e, request);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<GlobalExceptionResponse> handleMissingRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest request) {
        log.error("필수 파라미터 누락: {}", e.getMessage());
        return createErrorResponse(GlobalErrorCode.MISSING_REQUEST_PARAMETER, e, request);
    }

    @ExceptionHandler(GlobalCommonException.class)
    public ResponseEntity<Map<String, Object>> handleCustomException(GlobalCommonException e, HttpServletRequest request) {
        log.error("사용자 예외 처리: {}", e.getMessage());
        saveErrorLog(e.getErrorCode().getMsg(), e, request);
        Map<String, Object> response = new HashMap<>();
        response.put("message", e.getErrorCode().getMsg());
        response.put("code", e.getErrorCode().getCode());
        return new ResponseEntity<>(response, e.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalExceptionResponse> handleServerException(Exception e, HttpServletRequest request) {
        log.error("서버 내부 오류 발생: {}", e.getMessage());
        return createErrorResponse(GlobalErrorCode.INTERNAL_SERVER_ERROR, e, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<GlobalExceptionResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e, HttpServletRequest request) {
        log.error("데이터 무결성 위반: {}", e.getMessage());
        return createErrorResponse(GlobalErrorCode.DATA_INTEGRITY_VIOLATION, e, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error("유효성 검사 실패: {}", e.getMessage());
        return createErrorResponse(GlobalErrorCode.VALIDATION_FAIL, e, request);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<GlobalExceptionResponse> handleExpiredJwtException(ExpiredJwtException e, HttpServletRequest request) {
        log.error("만료된 JWT 토큰: {}", e.getMessage());
        return createErrorResponse(GlobalErrorCode.JWT_EXPIRED, e, request);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<GlobalExceptionResponse> handleJwtException(JwtException e, HttpServletRequest request) {
        log.error("유효하지 않은 JWT 토큰: {}", e.getMessage());
        return createErrorResponse(GlobalErrorCode.INVALID_TOKEN_ERROR, e, request);
    }

    private ResponseEntity<GlobalExceptionResponse> createErrorResponse(GlobalErrorCode errorCode, Exception e, HttpServletRequest request) {
        saveErrorLog(errorCode.getMsg(), e, request);
        GlobalExceptionResponse response = new GlobalExceptionResponse(new GlobalCommonException(errorCode).getErrorCode());
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    private void saveErrorLog(String message, Exception e, HttpServletRequest request) {
        try {
            Log logEntry = new Log();

            // 에러 정보
            logEntry.setStatus("ERROR");
            logEntry.setErrorMessage(e.getMessage());

            // 요청 정보
            logEntry.setUri(safeValue(request.getRequestURI()));
            logEntry.setMethod(safeValue(request.getMethod()));
            logEntry.setQueryString(safeValue(request.getQueryString()));

            // 유저 정보
            logEntry.setSessionId(safeValue(request.getRequestedSessionId()));
            logEntry.setUserAgent(safeValue(request.getHeader("User-Agent")));

            String loginId = "anonymousUser";

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated() &&
                    !"anonymousUser".equals(authentication.getPrincipal()) &&
                    !authentication.getPrincipal().toString().startsWith("stanl_2")
            ) {
                loginId = authentication.getPrincipal().toString();
            }

            logEntry.setLoginId(loginId);

            // 네트워크 정보
            logEntry.setIpAddress(safeValue(getClientIp(request)));
            logEntry.setHostName(safeValue(request.getRemoteHost()));
            logEntry.setRemotePort(request.getRemotePort());

            // Transaction ID
            String transactionId = UUID.randomUUID().toString();
            logEntry.setTransactionId(transactionId);

            logRepository.save(logEntry);

            // 임원 일시 메일 전송
            String pos =  memberQueryService.selectMemberInfo(loginId).getPosition();

            if("DIRECTOR".equals(pos) || "CEO".equals(pos)){
                memberQueryService.sendErrorMail(loginId, logEntry);
            }

        } catch (Exception ex) {
            log.error("로그 저장 중 오류 발생: {}", ex.getMessage(), ex);
        }
    }

    private String getClientIp(HttpServletRequest request) {
        String[] headers = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED_FOR"
        };

        for (String header : headers) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip.split(",")[0];
            }
        }
        return request.getRemoteAddr();
    }

    private String safeValue(String value) {
        return value != null ? value : "N/A";
    }
}
