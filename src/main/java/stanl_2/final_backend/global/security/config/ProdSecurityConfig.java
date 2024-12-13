package stanl_2.final_backend.global.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import stanl_2.final_backend.domain.log.command.repository.LogRepository;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.global.exception.GlobalCommonException;
import stanl_2.final_backend.global.exception.GlobalErrorCode;
import stanl_2.final_backend.global.security.filter.JWTTokenValidatorFilter;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Slf4j
@Configuration
@Profile("prod")
public class ProdSecurityConfig {

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    private final LogRepository logRepository;
    private final MemberQueryService memberQueryService;

    @Autowired
    public ProdSecurityConfig(LogRepository logRepository,
                              MemberQueryService memberQueryService) {
        this.logRepository = logRepository;
        this.memberQueryService = memberQueryService;
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {

        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        http.cors(corsConfig -> corsConfig.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())

                // 필터 순서: JWT 검증 -> CSRF
                .addFilterBefore(new JWTTokenValidatorFilter(jwtSecretKey, logRepository, memberQueryService), UsernamePasswordAuthenticationFilter.class)
                .requiresChannel(rcc -> rcc.anyRequest().requiresInsecure())
                // 인증 및 권한 예외를 처리
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            log.error("Authentication error: {}", authException.getMessage());
                            throw new GlobalCommonException(GlobalErrorCode.LOGIN_FAILURE);
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            log.error("Access denied: {}", accessDeniedException.getMessage());
                            throw new GlobalCommonException(GlobalErrorCode.UNAUTHORIZED);
                        }));
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());

        // 접근 제어
        RequestMatcherConfig.configureRequestMatchers(http);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Collections.singletonList("https://stanl2motive.com"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-XSRF-TOKEN"));
        config.setExposedHeaders(Collections.singletonList("Authorization"));
        config.setMaxAge(3600L);

        return request -> config;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService){

        ProdUsernamePwdAuthenticationProvider authenticationProvider =
                new ProdUsernamePwdAuthenticationProvider(userDetailsService, passwordEncoder());

        ProviderManager providerManager = new ProviderManager(authenticationProvider);

        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }
}
