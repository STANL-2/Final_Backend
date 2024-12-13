package stanl_2.final_backend.global.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import stanl_2.final_backend.global.config.datasource.ReplicationRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {
    @ConfigurationProperties(prefix = "spring.datasource.writer.hikari")
    @Bean(name = "writerDataSource")
    public DataSource writerDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @ConfigurationProperties(prefix = "spring.datasource.reader.hikari")
    @Bean(name = "readerDataSource")
    public DataSource readerDataSource() {
        // DataSourceBuilder를 사용해 HikariDataSource 타입의 데이터 소스를 생성합니다.
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @DependsOn({"writerDataSource", "readerDataSource"})
    @Bean
    public DataSource routingDataSource(
            @Qualifier("writerDataSource") DataSource writer,
            @Qualifier("readerDataSource") DataSource reader) {

        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();

        dataSourceMap.put("writer", writer);  // 쓰기 전용 데이터 소스
        dataSourceMap.put("reader", reader);  // 읽기 전용 데이터 소스

        routingDataSource.setTargetDataSources(dataSourceMap); // 대상 데이터 소스 설정
        routingDataSource.setDefaultTargetDataSource(writer); // 기본 데이터 소스 설정
        routingDataSource.afterPropertiesSet();

        return routingDataSource;
    }

    @DependsOn("routingDataSource")
    @Primary  // 이 빈을 기본 `DataSource`로 설정
    @Bean
    public DataSource dataSource(DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }
}
