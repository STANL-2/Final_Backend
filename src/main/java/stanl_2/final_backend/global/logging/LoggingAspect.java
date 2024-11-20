package stanl_2.final_backend.global.logging;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Before("within(@org.springframework.web.bind.annotation.RestController *)")
    public void logRequestInfo(JoinPoint joinPoint) {
        // HttpServletRequest를 가져옵니다.
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) return;

        HttpServletRequest request = attributes.getRequest();
        String ipAddress = request.getRemoteAddr();
        String apiEndpoint = request.getRequestURI();
        String method = request.getMethod();
        String userPk = request.getHeader("User-Pk"); // 예시 헤더로 사용자 PK 전달
        String requestId = UUID.randomUUID().toString();

        // 로그 출력
        log.info("Request Info: IP={}, API={}, Method={}, UserPK={}, RequestID={}",
                ipAddress, apiEndpoint, method, userPk, requestId);
    }
}
