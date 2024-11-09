package stanl_2.final_backend.global.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "stanl_2.final_backend", annotationClass = Mapper.class)
public class MybatisConfig {
}
