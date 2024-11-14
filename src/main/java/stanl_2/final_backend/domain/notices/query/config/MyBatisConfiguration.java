package stanl_2.final_backend.domain.notices.query.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration("noticeMybatisConfiguration")
@MapperScan(basePackages = "stanl_2.final_backend.domain.notices.query.repository")
public class MyBatisConfiguration {
}