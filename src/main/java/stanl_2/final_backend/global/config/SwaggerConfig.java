package stanl_2.final_backend.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "FinalBackend API Specification",
                description = "Specification for buds",
                version = "v1"
        )
)
@Configuration
public class SwaggerConfig {
}
