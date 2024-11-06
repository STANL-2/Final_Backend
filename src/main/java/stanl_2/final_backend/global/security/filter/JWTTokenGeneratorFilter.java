package stanl_2.final_backend.global.security.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import stanl_2.final_backend.domain.member.aggregate.entity.Member;
import stanl_2.final_backend.global.security.constants.ApplicationConstants;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

    private final ApplicationConstants applicationConstants;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("g1");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("g2");
        if (null != authentication) {
            Member member = (Member) authentication.getPrincipal();
            log.info("g3");

            // Base64로 인코딩된 키를 디코딩하여 SecretKey 생성
            SecretKey secretKey = Keys.hmacShaKeyFor(applicationConstants.getJWT_SECRET_KEY());
            log.info("g4");

            String jwt = Jwts.builder()
                    .setIssuer("STANL2")
                    .setSubject("JWT Token")
//                    .claim("username", authentication.getName())
//                    .claim("id", member.getId())

                    .claim("authorities", authentication.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + 30000000))
                    .signWith(secretKey)
                    .compact(); // Digital Signature 생성

            log.info("g5");
            // JWT 토큰을 응답 헤더에 추가
            response.setHeader(applicationConstants.getJWT_HEADER(), jwt);
        }
        log.info("g6");
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/api/v1/auth/signin");
    }
}
