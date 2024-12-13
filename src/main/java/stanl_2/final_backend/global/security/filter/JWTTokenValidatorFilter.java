package stanl_2.final_backend.global.security.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import stanl_2.final_backend.domain.log.command.aggregate.Log;
import stanl_2.final_backend.domain.log.command.repository.LogRepository;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.global.exception.GlobalCommonException;
import stanl_2.final_backend.global.exception.GlobalErrorCode;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public class JWTTokenValidatorFilter extends OncePerRequestFilter {

    private final String jwtSecretKey;
    private final LogRepository logRepository; // 로그 저장소
    private final MemberQueryService memberQueryService;

    public JWTTokenValidatorFilter(String jwtSecretKey,
                                   LogRepository logRepository,
                                   MemberQueryService memberQueryService) {
        this.jwtSecretKey = jwtSecretKey;
        this.logRepository = logRepository; // DI 주입
        this.memberQueryService = memberQueryService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            try {
                SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                // 사용자 정보 추출
                String username = claims.get("username", String.class);
                String authorities = claims.get("authorities", String.class);

                if (username == null || authorities == null || authorities.isEmpty()) {
                    log.warn("토큰에 필수 정보가 누락되었습니다. username: {}, authorities: {}", username, authorities);
                    handleInvalidToken(response, "토큰에 필수 정보가 없습니다.", new JwtException("Missing required claims"), request);
                    return;
                }

                List<GrantedAuthority> grantedAuthorities = Arrays.stream(authorities.split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        username, null, grantedAuthorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (JwtException e) {
                log.error("JWT 파싱 실패: {}", e.getMessage());
                handleInvalidToken(response, "유효하지 않은 토큰입니다.", e, request);
                return;
            }
        } else if (isExemptedPath(request)) {
            log.debug("예외 경로 요청. JWT 검증 생략. 요청 URL: {}", request.getRequestURI());
        } else {
            log.warn("Authorization 헤더가 없거나 올바르지 않은 형식입니다.");
            handleInvalidToken(response, "인증 정보가 없습니다.", new JwtException("Missing Authorization Header"), request);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void handleInvalidToken(HttpServletResponse response, String message, Exception e, HttpServletRequest request) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        String jsonResponse = String.format(
                "{\"code\": 40100, \"msg\": \"%s\", \"httpStatus\": \"UNAUTHORIZED\"}",
                message
        );

        response.getWriter().write(jsonResponse);

        log.error("요청 거부됨: {}. Error: {}. Request URI: {}, Method: {}, Client IP: {}",
                message, e.getMessage(), request.getRequestURI(), request.getMethod(), getClientIp(request));

        saveErrorLog(message, e, request);
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
            log.error("로그 저장 실패: {}", ex.getMessage());
            throw new GlobalCommonException(GlobalErrorCode.FAIL_LOG_SAVE);
        }
    }

    private String getClientIp(HttpServletRequest request) {
        String[] headers = {"X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
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



    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return isExemptedPath(request);
    }

    private boolean isExemptedPath(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.equals("/api/v1/auth/signin") ||
                path.equals("/api/v1/auth/signup") ||
                path.equals("/api/v1/auth/checkmail") ||
                path.equals("/api/v1/auth/checknum") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-resources") ||
                path.startsWith("/webjars");
    }

}
