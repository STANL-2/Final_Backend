package stanl_2.final_backend.domain.customer.query.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration("customerMybatisConfiguration")
@MapperScan(basePackages = "stanl_2.final_backend.domain.customer.query.repository")
public class MybatisConfiguration {
}
