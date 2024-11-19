package stanl_2.final_backend.domain.certification.query.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration("certificationMybatisConfiguration")
@MapperScan(basePackages = "stanl_2.final_backend.domain.certification.query.repository")
public class MybatisConfiguration {
}
