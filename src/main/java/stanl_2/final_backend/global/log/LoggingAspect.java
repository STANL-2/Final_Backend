package stanl_2.final_backend.global.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import stanl_2.final_backend.global.log.repository.LogRepository;
import stanl_2.final_backend.global.log.aggregate.Log;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    private final ObjectMapper objectMapper;
    private final LogRepository logRepository;

    @Autowired
    public LoggingAspect(LogRepository logRepository) {
        this.objectMapper = new ObjectMapper();
        this.logRepository = logRepository;
    }

    @Before("within(@org.springframework.web.bind.annotation.RestController *)")
    public void logRequestInfo(JoinPoint joinPoint) {
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
        requestData.put("headers", getHeaders(request));
        logData.put("request", requestData);

        // 시간 정보
        Map<String, Object> timeData = new HashMap<>();
        timeData.put("request_time", LocalDateTime.now().toString());
        logData.put("time", timeData);

        // 추가 정보
        logData.put("transaction_id", UUID.randomUUID().toString());

        try {
            log.info("Request Info: {}", objectMapper.writeValueAsString(logData));

            // 로그 엔티티 저장
            Log logEntry = new Log();
            logEntry.setSessionId(safeValue(request.getRequestedSessionId()));
            logEntry.setUserAgent(safeValue(request.getHeader("User-Agent")));
            logEntry.setIpAddress(safeValue(getClientIp(request)));
            logEntry.setUri(safeValue(request.getRequestURI()));
            logEntry.setMethod(safeValue(request.getMethod()));
            logEntry.setQueryString(safeValue(request.getQueryString()));
            logEntry.setRequestTime(LocalDateTime.now());

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

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        request.getHeaderNames().asIterator().forEachRemaining(headerName ->
                headers.put(headerName, safeValue(request.getHeader(headerName)))
        );
        return headers;
    }

    private String safeValue(String value) {
        return value != null ? value : "N/A";
    }
}
