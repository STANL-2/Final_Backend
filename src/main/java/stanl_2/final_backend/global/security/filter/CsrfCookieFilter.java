package stanl_2.final_backend.global.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import stanl_2.final_backend.global.exception.CommonException;
import stanl_2.final_backend.global.exception.ErrorCode;

import java.io.IOException;

@Slf4j
public class CsrfCookieFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfCookieFilter.class.getName());

        if(csrfToken != null) {
            Cookie csrfCookie = null;

            // 요청에서 쿠키 배열 가져오기 (null 체크 추가)
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                // 쿠키 배열에서 "XSRF-TOKEN" 이름을 가진 쿠키를 찾음
                for (Cookie cookie : cookies) {
                    if ("XSRF-TOKEN".equals(cookie.getName())) {
                        csrfCookie = cookie;
                        break; // 쿠키를 찾으면 반복문 종료
                    }
                }
            }

            // 쿠키가 없거나, 토큰이 변경된 경우 새로운 쿠키 생성
            if (csrfCookie == null || !csrfCookie.getValue().equals(csrfToken.getToken())) {
                Cookie newCsrfCookie = new Cookie("XSRF-TOKEN", csrfToken.getToken());
                newCsrfCookie.setPath("/");
                newCsrfCookie.setHttpOnly(false); // JavaScript에서 접근 가능하도록 설정
                newCsrfCookie.setSecure(request.isSecure()); // HTTPS 요청에서만 쿠키 전송
                newCsrfCookie.setMaxAge(-1); // 세션 종료 시 삭제
                response.addCookie(newCsrfCookie); // 응답에 쿠키 추가
            }
        } else {
            throw new CommonException(ErrorCode.NOT_FOUND_CSRF_TOKEN);
        }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.startsWith("/api/v1/auth") ||
                path.startsWith("/api/v1/sample");
    }
}
