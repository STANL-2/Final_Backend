package stanl_2.final_backend.global.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
public class CsrfCookieFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // CsrfToken은 Spring Security에서 CSRF 보호를 위한 토큰
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        if (csrfToken != null) {
            // 기존 쿠키 확인
            Cookie[] cookies = request.getCookies();
            Optional<Cookie> csrfCookie = Optional.empty();

            if (cookies != null) {
                csrfCookie = Arrays.stream(cookies)
                        .filter(cookie -> "XSRF-TOKEN".equals(cookie.getName()))
                        .findFirst();
            }

            // 쿠키가 존재하지 않거나 토큰이 변경된 경우 쿠키 업데이트
            if (!csrfCookie.isPresent() || !csrfCookie.get().getValue().equals(csrfToken.getToken())) {
                // 새로운 CSRF 토큰을 쿠키로 설정
                Cookie newCsrfCookie = new Cookie("XSRF-TOKEN", csrfToken.getToken());
                newCsrfCookie.setPath("/");
                newCsrfCookie.setHttpOnly(false);  // JS에서도 접근할 수 있도록 설정
                newCsrfCookie.setSecure(request.isSecure());  // HTTPS인 경우에만 secure 설정
                newCsrfCookie.setMaxAge(-1);  // 세션이 종료될 때 쿠키가 삭제되도록 설정

                response.addCookie(newCsrfCookie);  // 응답에 쿠키 추가
            }
        } else {
            throw new CommonException(ErrorCode.NOT_FOUND_CSRF_TOKEN);
        }


        filterChain.doFilter(request, response);
    }
}
