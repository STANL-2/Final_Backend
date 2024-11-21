package stanl_2.final_backend.domain.log.query.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration("logMybatisConfiguration")
@MapperScan(basePackages = "stanl_2.final_backend.domain.log.query.repository")
public class MybatisConfiguration {
}
