package stanl_2.final_backend.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@OpenAPIDefinition(
        info = @Info(
                title = "Motive_BE API 명세서",
                description = "Motive 프로젝트 API 명세서",
                version = "v1"
        )
)
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

//        @Bean
//        @Profile("!Prod")
//        public GroupedOpenApi GroupedOpenApi(){
//
//                String jwtSchemaName = "Bear 토큰 입력";
//                String[] paths = {"../../domain/center/**"};
//
//                return GroupedOpenApi
//                        .builder()
//                        .group("영업 매장 관련 API")
//                        .pathsToMatch(paths)
//                        .addOpenApiCustomizer(buildSecurityOpenApi()).build();
//        }

//        public OpenApiCustomizer buildSecurityOpenApi() {
//                // jwt token을 한번 설정하면 header에 값을 넣어주는 코드
//                return OpenApi -> OpenApi.addSecurityItem(new SecurityRequirement().addList("jwt token"))
//                        .getComponents().addSecuritySchemes("jwt token", new SecurityScheme()
//                                .name("Authorization")
//                                .type(SecurityScheme.Type.HTTP)
//                                .in(SecurityScheme.In.HEADER)
//                                .bearerFormat("JWT")
//                                .scheme("bearer"));
//        }
}
