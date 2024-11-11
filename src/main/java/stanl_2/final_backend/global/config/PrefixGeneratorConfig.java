package stanl_2.final_backend.global.config;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;
import java.util.stream.Stream;

@Slf4j
public class PrefixGeneratorConfig implements IdentifierGenerator {

    private String prefix;

    @Override
    public void configure(Type type, Properties properties, ServiceRegistry serviceRegistry) {
        this.prefix = properties.getProperty("prefix");
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
        try {

            // 엔티티 메타데이터를 사용하여 쿼리 생성
            String entityName = obj.getClass().getSimpleName();
            String idProperty = session.getEntityPersister(obj.getClass().getName(), obj)
                    .getIdentifierPropertyName();

            // ID 조회 쿼리 생성 - 식별자 필드만 조회
            String query = String.format("SELECT e.%s FROM %s e", idProperty, entityName);

            // Hibernate 6.x에 맞는 쿼리 실행 방식
            Stream<String> ids = session.createSelectionQuery(query, String.class).getResultStream();

            // 현재 ID 중 최대 값을 가져와서 새로운 ID 생성
            long max = ids
                    .map(id -> id.replace(prefix + "_", ""))
                    .mapToLong(Long::parseLong)
                    .max()
                    .orElse(0L);

            // 새로운 ID 생성
            return prefix + "_" + String.format("%09d", max + 1);
        } catch (Exception e) {
            log.error("Error generating ID", e);
            throw new HibernateException("Failed to generate ID", e);
        }
    }
}
