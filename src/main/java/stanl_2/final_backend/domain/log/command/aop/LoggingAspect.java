package stanl_2.final_backend.domain.log.command.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import stanl_2.final_backend.domain.log.command.aggregate.Log;
import stanl_2.final_backend.domain.log.command.repository.LogRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    private final LogRepository logRepository;
    private static final ThreadLocal<String> currentTransactionId = new ThreadLocal<>();

    @Autowired
    public LoggingAspect(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @AfterReturning("within(@org.springframework.web.bind.annotation.RestController *)")
    public void logRequestSuccess(JoinPoint joinPoint) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) return;

        HttpServletRequest request = attributes.getRequest();

        Map<String, Object> logData = new HashMap<>();

        // 사용자 정보
        Map<String, Object> userData = new HashMap<>();
        userData.put("session_id", safeValue(request.getRequestedSessionId()));
        userData.put("user_agent", safeValue(request.getHeader("User-Agent")));
        logData.put("user", userData);

        // 네트워크 정보
        Map<String, Object> networkData = new HashMap<>();
        networkData.put("ip_address", safeValue(getClientIp(request)));
        networkData.put("host_name", safeValue(request.getRemoteHost()));
        networkData.put("remote_port", request.getRemotePort());
        logData.put("network", networkData);

        // 요청 정보
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("uri", safeValue(request.getRequestURI()));
        requestData.put("method", safeValue(request.getMethod()));
        requestData.put("query_string", safeValue(request.getQueryString()));
        logData.put("request", requestData);


        // 추가 정보
        String transactionId = UUID.randomUUID().toString();
        currentTransactionId.set(transactionId);
        logData.put("transaction_id", transactionId);

        try {
            // 로그 엔티티 저장
            Log logEntry = new Log();

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

            // 요청 정보
            logEntry.setUri(safeValue(request.getRequestURI()));
            logEntry.setMethod(safeValue(request.getMethod()));
            logEntry.setQueryString(safeValue(request.getQueryString()));

            // 추가적인 정보
            logEntry.setTransactionId(transactionId);

            logEntry.setStatus("SUCCESS");
            logRepository.save(logEntry);

        } catch (Exception e) {
            log.error("Failed to log request info", e);
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
