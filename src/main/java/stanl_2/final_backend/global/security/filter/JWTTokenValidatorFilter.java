package stanl_2.final_backend.global.security.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import stanl_2.final_backend.global.exception.CommonException;
import stanl_2.final_backend.global.exception.ErrorCode;
import stanl_2.final_backend.global.security.constants.ApplicationConstants;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@RequiredArgsConstructor
public class JWTTokenValidatorFilter extends OncePerRequestFilter {

    private final ApplicationConstants applicationConstants;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("SESSIONID".equals(cookie.getName())) {
                    String jwt = cookie.getValue();

                    try {
                        // 비밀 키를 Base64로 디코딩하여 생성
                        byte[] decodedKey = Base64.getDecoder().decode(applicationConstants.getJWT_SECRET_KEY());
                        SecretKey secretKey = Keys.hmacShaKeyFor(decodedKey);

                        Claims claims = Jwts.parserBuilder()
                                .setSigningKey(secretKey)
                                .build()
                                .parseClaimsJws(jwt)
                                .getBody();

                        String authorities = (String) claims.get("authorities");
                        Authentication authentication = new UsernamePasswordAuthenticationToken(
                                claims.getSubject(), null,
                                AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } catch (Exception e) {
                        throw new CommonException(ErrorCode.INVALID_TOKEN_ERROR);
                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }

//        String jwt = request.getHeader(applicationConstants.getJWT_HEADER());
//        if (null != jwt) {
//            try {
//                // 비밀키 가져오기
//                SecretKey secretKey = Keys.hmacShaKeyFor(applicationConstants.getJWT_SECRET_KEY());
//                String jwtToken = jwt.substring(7);
//                // JWT 토큰 검증
//                Claims claims = Jwts.parserBuilder()
//                        .setSigningKey(secretKey)
//                        .build()
//                        .parseClaimsJws(jwtToken)
//                        .getBody();
//
//                String username = String.valueOf(claims.get("username"));
//                String authorities = String.valueOf(claims.get("authorities"));
//                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,
//                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
//
//                // SecurityContextHolder에 저장
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            } catch (Exception exception) {
//                throw new CommonException(ErrorCode.INVALID_TOKEN_ERROR);
//            }
//        }
//        filterChain.doFilter(request, response);
//    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.equals("/api/v1/auth/signin") ||
                path.equals("/api/v1/auth/signup");
    }
}
