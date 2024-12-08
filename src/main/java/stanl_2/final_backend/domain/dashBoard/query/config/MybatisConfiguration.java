package stanl_2.final_backend.domain.dashBoard.query.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration("dashBoardMybatisConfiguration")
@MapperScan(basePackages = "stanl_2.final_backend.domain.dashBoard.query.repository")
public class MybatisConfiguration {
}
