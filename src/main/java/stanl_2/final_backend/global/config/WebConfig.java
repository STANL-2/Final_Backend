package stanl_2.final_backend.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // API 경로 매핑
                .allowedOrigins("https://stanl2motive.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
                .allowedHeaders("Content-Type", "Authorization") // 허용할 요청 헤더
                .exposedHeaders("Authorization") // 응답 헤더 노출
                .allowCredentials(true) // 인증 정보 허용
                .maxAge(3600); // Preflight 요청 캐싱 시간
    }
}
