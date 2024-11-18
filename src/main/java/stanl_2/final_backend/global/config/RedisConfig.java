package stanl_2.final_backend.global.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class RedisConfig {

    private final RedisConnectionFactory redisConnectionFactory;

    public RedisConfig(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @PostConstruct
    public void testRedisConnection() {
        try {
            redisConnectionFactory.getConnection().ping();
            System.out.println("✅ Successfully connected to Redis");
        } catch (Exception e) {
            System.err.println("❌ Failed to connect to Redis: " + e.getMessage());
        }
    }
}