package stanl_2.final_backend.domain.family.query.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration("familyMybatisConfiguration")
@MapperScan(basePackages = "stanl_2.final_backend.domain.family.query.repository")
public class MybatisConfiguration {
}
