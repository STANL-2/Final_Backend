package stanl_2.final_backend.domain.education.query.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration("educationMybatisConfiguration")
@MapperScan(basePackages = "stanl_2.final_backend.domain.education.query.repository")
public class MybatisConfiguration {
}
