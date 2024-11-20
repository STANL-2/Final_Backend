package stanl_2.final_backend.global.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import stanl_2.final_backend.domain.log.command.aggregate.Log;
import stanl_2.final_backend.domain.log.command.repository.LogRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class LoggingFilter implements Filter {

    private final ObjectMapper objectMapper;
    private final LogRepository logRepository;
    private static final ThreadLocal<String> currentTransactionId = new ThreadLocal<>();

    @Autowired
    public LoggingFilter(LogRepository logRepository) {
        this.objectMapper = new ObjectMapper();
        this.logRepository = logRepository;
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 필터 초기화 로직 (필요 시 구현)
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest)) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Map<String, Object> logData = new HashMap<>();

        // 사용자 정보
        Map<String, Object> userData = new HashMap<>();
        userData.put("session_id", safeValue(httpRequest.getRequestedSessionId()));
        userData.put("user_agent", safeValue(httpRequest.getHeader("User-Agent")));
        logData.put("user", userData);

        // 네트워크 정보
        Map<String, Object> networkData = new HashMap<>();
        networkData.put("ip_address", safeValue(getClientIp(httpRequest)));
        networkData.put("host_name", safeValue(httpRequest.getRemoteHost()));
        networkData.put("remote_port", httpRequest.getRemotePort());
        logData.put("network", networkData);

        // 요청 정보
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("uri", safeValue(httpRequest.getRequestURI()));
        requestData.put("method", safeValue(httpRequest.getMethod()));
        requestData.put("query_string", safeValue(httpRequest.getQueryString()));
        logData.put("request", requestData);

        // 시간 정보
        Map<String, Object> timeData = new HashMap<>();
        timeData.put("request_time", LocalDateTime.now().toString());
        logData.put("time", timeData);

        // 추가 정보
        String transactionId = UUID.randomUUID().toString();
        currentTransactionId.set(transactionId);
        logData.put("transaction_id", transactionId);

        try {
            log.info("Request Info: {}", objectMapper.writeValueAsString(logData));

            // 로그 엔티티 저장
            Log logEntry = new Log();

            // User Information
            logEntry.setSessionId(safeValue(httpRequest.getRequestedSessionId()));
            logEntry.setUserAgent(safeValue(httpRequest.getHeader("User-Agent")));

            // Network Information
            logEntry.setIpAddress(safeValue(getClientIp(httpRequest)));
            logEntry.setHostName(safeValue(httpRequest.getRemoteHost()));
            logEntry.setRemotePort(httpRequest.getRemotePort());

            // Request Information
            logEntry.setUri(safeValue(httpRequest.getRequestURI()));
            logEntry.setMethod(safeValue(httpRequest.getMethod()));
            logEntry.setQueryString(safeValue(httpRequest.getQueryString()));

            // Time Information
            logEntry.setRequestTime(LocalDateTime.now());

            // Additional Information
            logEntry.setTransactionId(transactionId);

            // Status 초기 설정 (요청만 기록한 상태)
            logEntry.setStatus("IN_PROGRESS");

            logRepository.save(logEntry);

        } catch (Exception e) {
            log.error("Failed to log request info", e);
        }

        try {
            chain.doFilter(request, response);
            logRequestSuccess();
        } catch (Exception e) {
            logRequestFailure(e);
            throw e;
        } finally {
            currentTransactionId.remove();
        }
    }

    @Override
    public void destroy() {
        // 필터 해제 로직 (필요 시 구현)
    }

    public static String getCurrentTransactionId() {
        return currentTransactionId.get();
    }

    private void logRequestSuccess() {
        String transactionId = currentTransactionId.get();
        if (transactionId == null) return;

        try {
            Log logEntry = logRepository.findByTransactionId(transactionId);
            if (logEntry != null) {
                logEntry.setStatus("SUCCESS");
                logRepository.save(logEntry);
            }
        } catch (Exception e) {
            log.error("Failed to update log status to SUCCESS", e);
        }
    }

    private void logRequestFailure(Throwable e) {
        String transactionId = currentTransactionId.get();
        if (transactionId == null) return;

        try {
            Log logEntry = logRepository.findByTransactionId(transactionId);
            if (logEntry != null) {
                logEntry = new Log();
                logEntry.setTransactionId(transactionId);
            }
            logEntry.setStatus("FAILURE");
            logEntry.setErrorMessage(e.getMessage());
            logRepository.save(logEntry);
        } catch (Exception ex) {
            log.error("Failed to update log status to FAILURE", ex);
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

    private void ensureTransactionId() {
        if (currentTransactionId.get() == null) {
            currentTransactionId.set(UUID.randomUUID().toString());
        }
    }
}
