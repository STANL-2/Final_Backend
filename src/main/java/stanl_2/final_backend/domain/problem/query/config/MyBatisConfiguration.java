package stanl_2.final_backend.domain.problem.query.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration("problemMybatisConfiguration")
@MapperScan(basePackages = "stanl_2.final_backend.domain.problem.query.repository")
public class MyBatisConfiguration {
}
