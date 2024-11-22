package stanl_2.final_backend.domain.promotion.query.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration("promotionMybatisConfiguration")
@MapperScan(basePackages = "stanl_2.final_backend.domain.promotion.query.repository")
public class MyBatisConfiguration {
}