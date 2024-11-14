package stanl_2.final_backend.global.security.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import stanl_2.final_backend.global.exception.GlobalCommonException;
import stanl_2.final_backend.global.exception.GlobalErrorCode;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class JWTTokenValidatorFilter extends OncePerRequestFilter {

    private final String jwtSecretKey;
    private final String jwtHeader;

    public JWTTokenValidatorFilter(String jwtSecretKey, String jwtHeader) {
        this.jwtHeader = jwtHeader;
        this.jwtSecretKey = jwtSecretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 요청 헤더에서 JWT 토큰 가져오기
        String jwt = request.getHeader(jwtHeader);

        // 토큰이 존재하고 "Bearer "로 시작하는지 확인
        if (jwt != null && jwt.startsWith("Bearer ")) {
            try {
                // 비밀키를 사용하여 JWT 토큰을 검증
                SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
                String jwtToken = jwt.substring(7); // "Bearer " 부분 제거

                // JWT 토큰 파싱 및 검증
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(jwtToken)
                        .getBody();

                // 클레임에서 토큰 타입 확인
                String subject = claims.getSubject();

                // Access Token 처리
                if ("Access Token".equals(subject)) {
                    handleAccessToken(claims, request);
                }
                // Refresh Token은 인증 처리가 아닌, 토큰 재발급용으로만 사용
                else if ("Refresh Token".equals(subject)) {
                    log.info("Received Refresh Token");
                } else {
                    throw new GlobalCommonException(GlobalErrorCode.INVALID_TOKEN_ERROR);
                }

            } catch (Exception exception) {
                log.error("Invalid JWT Token", exception);
                throw new GlobalCommonException(GlobalErrorCode.INVALID_TOKEN_ERROR);
            }
        }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }

    private void handleAccessToken(Claims claims, HttpServletRequest request) {
        // 클레임에서 사용자 정보 및 권한 추출
//        String id = claims.get("id", String.class);
        String username = claims.get("username", String.class);
        String authorities = claims.get("authorities", String.class);

        // SecurityContext에 인증 정보 설정
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username, null,
                AuthorityUtils.commaSeparatedStringToAuthorityList(authorities)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("Authenticated user: {}", username);

        // 추가적으로, 추출된 정보를 request 속성에 설정
//        request.setAttribute("id", id);
        request.setAttribute("username", username);
        request.setAttribute("authorities", authorities);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // 특정 경로에서는 필터를 적용하지 않음
        String path = request.getServletPath();
        return path.startsWith("/api/v1/auth") ||
                path.startsWith("/api/v1/sample");
    }
}
