package stanl_2.final_backend.global.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import stanl_2.final_backend.global.exception.GlobalCommonException;
import stanl_2.final_backend.global.exception.GlobalErrorCode;

import java.io.IOException;

@Slf4j
public class CsrfCookieFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        if (csrfToken == null) {
            log.error("CSRF 토큰을 찾을 수 없습니다.");
            throw new GlobalCommonException(GlobalErrorCode.NOT_FOUND_CSRF_TOKEN);
        }

        // 쿠키에서 CSRF 토큰 가져오기
        String csrfCookieValue = getCsrfTokenFromCookie(request);
        log.info("쿠키에서 가져온 CSRF 토큰: {}", csrfCookieValue);
        log.info("현재 요청의 CSRF 토큰: {}", csrfToken.getToken());

        if (csrfCookieValue == null || !csrfCookieValue.equals(csrfToken.getToken())) {
            log.error("CSRF 토큰 불일치 - 쿠키와 요청의 토큰이 일치하지 않습니다.");
            throw new GlobalCommonException(GlobalErrorCode.NOT_FOUND_CSRF_TOKEN);
        }

        filterChain.doFilter(request, response);
    }

    private String getCsrfTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("XSRF-TOKEN".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/api/v1/auth") ||
                path.startsWith("/api/v1/sample") ||
                path.startsWith("/swagger-ui/") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-resources") ||
                path.startsWith("/webjars");
    }
}