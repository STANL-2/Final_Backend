package stanl_2.final_backend.domain.member.query.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration("memberMybatisConfiguration")
@MapperScan(basePackages = "stanl_2.final_backend.domain.member.query.repository")
public class MybatisConfiguration {
}
