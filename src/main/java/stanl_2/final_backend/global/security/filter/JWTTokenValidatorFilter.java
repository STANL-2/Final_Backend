package stanl_2.final_backend.global.security.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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

    public JWTTokenValidatorFilter(String jwtSecretKey, LogRepository logRepository) {
        this.jwtSecretKey = jwtSecretKey;
        this.logRepository = logRepository; // DI 주입
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
                    handleInvalidToken(response, "토큰에 필수 정보가 없습니다.", new JwtException("Missing required claims"));
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
                handleInvalidToken(response, "유효하지 않은 토큰입니다.", e);
                return;
            }
        } else if (isExemptedPath(request)) {
            log.debug("예외 경로 요청. JWT 검증 생략. 요청 URL: {}", request.getRequestURI());
        } else {
            log.warn("Authorization 헤더가 없거나 올바르지 않은 형식입니다.");
            handleInvalidToken(response, "인증 정보가 없습니다.", new JwtException("Missing Authorization Header"));
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void handleInvalidToken(HttpServletResponse response, String message, Exception e) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
        log.error("요청 거부됨: {}", message);

        // 로그 저장 호출
        saveErrorLog(message, e);
    }

    private void saveErrorLog(String message, Exception e) {
        try {
            Log logEntity = new Log();
            logEntity.setTransactionId(UUID.randomUUID().toString());
            logEntity.setStatus("ERROR");
            logEntity.setErrorMessage(message + " - " + e.getMessage());
            logRepository.save(logEntity);
        } catch (Exception ex) {
            log.error("로그 저장 실패: {}", ex.getMessage());
        }
    }

    private void validateAndSetAuthentication(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.get("username", String.class);
        String authorities = claims.get("authorities", String.class);

        if (username == null || authorities == null || authorities.isEmpty()) {
            log.warn("토큰에 필수 정보가 누락되었습니다. username: {}, authorities: {}", username, authorities);
            throw new JwtException("토큰에 필수 정보가 없습니다.");
        }

        List<GrantedAuthority> grantedAuthorities = Arrays.stream(authorities.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return isExemptedPath(request);
    }

    private boolean isExemptedPath(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.equals("/api/v1/auth/signin") ||
                path.equals("/api/v1/auth/signup") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-resources") ||
                path.startsWith("/webjars") ||
                path.equals("/api/v1/auth");
    }

}
