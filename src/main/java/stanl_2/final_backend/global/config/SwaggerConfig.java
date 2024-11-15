package stanl_2.final_backend.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.Cookie;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Motive_BE API 명세서",
                description = "Motive 프로젝트 API 명세서",
                version = "v1"
        ),
        security = {@SecurityRequirement(name = "Bearer Authentication")}
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {

        @Bean
        public OpenAPI OpenAPI() {
                return new OpenAPI()
                        .components(new Components()
                                .addSecuritySchemes("Bearer Authentication",
                                        new io.swagger.v3.oas.models.security.SecurityScheme()
                                                .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                        )
                        .addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement()
                                .addList("Bearer Authentication"));
        }

        @Bean
        public WebMvcConfigurer swaggerCsrfConfigurer() {
                return new WebMvcConfigurer() {
                        @Override
                        public void addInterceptors(InterceptorRegistry registry) {
                                registry.addInterceptor(new HandlerInterceptor() {
                                        @Override
                                        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                                                Cookie[] cookies = request.getCookies();
                                                if (cookies != null) {
                                                        String csrfToken = Arrays.stream(cookies)
                                                                .filter(cookie -> "XSRF-TOKEN".equals(cookie.getName()))
                                                                .map(Cookie::getValue)
                                                                .findFirst()
                                                                .orElse(null);
                                                        if (csrfToken != null) {
                                                                response.addHeader("X-XSRF-TOKEN", csrfToken);
                                                        }
                                                }
                                                return true;
                                        }
                                }).addPathPatterns("/swagger-ui/**");
                        }
                };
        }
}
