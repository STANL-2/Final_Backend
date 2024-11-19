package stanl_2.final_backend.domain.career.query.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration("careerMybatisConfiguration")
@MapperScan(basePackages = "stanl_2.final_backend.domain.career.query.repository")
public class MybatisConfiguration {
}
