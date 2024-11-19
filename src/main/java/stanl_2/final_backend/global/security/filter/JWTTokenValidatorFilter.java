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
import stanl_2.final_backend.global.exception.GlobalCommonException;
import stanl_2.final_backend.global.exception.GlobalErrorCode;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class JWTTokenValidatorFilter extends OncePerRequestFilter {

    private final String jwtSecretKey;

    public JWTTokenValidatorFilter(String jwtSecretKey) {
        this.jwtSecretKey = jwtSecretKey;
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

                // 예외 처리: 토큰에 유효한 권한이 없을 경우
                if (username == null || authorities == null || authorities.isEmpty()) {
                    throw new GlobalCommonException(GlobalErrorCode.UNAUTHORIZED);
                }

                List<GrantedAuthority> grantedAuthorities = Arrays.stream(authorities.split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                // SecurityContext에 인증 정보 설정
                if (username != null) {
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            username, null, grantedAuthorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (JwtException e) {
                log.error("유효하지 않은 토큰입니다: {}", e.getMessage());
                throw new GlobalCommonException(GlobalErrorCode.INVALID_TOKEN_ERROR);
            }
        }else {
            // 토큰이 없을 경우 예외 처리
            throw new GlobalCommonException(GlobalErrorCode.LOGIN_FAILURE);
        }

        filterChain.doFilter(request, response);
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // 특정 경로에서는 필터를 적용하지 않음
        String path = request.getServletPath();
        return path.equals("/api/v1/auth/signin") ||
                path.equals("/api/v1/auth/signup") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-resources") ||
                path.startsWith("/webjars") ||
                path.equals("/api/v1/auth");    // 권한 부여때문에(일단 열어둠)
    }
}
