package stanl_2.final_backend.global.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class RedisConfig {

    private final RedisConnectionFactory redisConnectionFactory;

    public RedisConfig(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void testRedisConnection() {
        try {
            redisConnectionFactory.getConnection().ping();
            System.out.println("✅ Successfully connected to Redis");
        } catch (Exception e) {
            System.err.println("❌ Failed to connect to Redis: " + e.getMessage());
        }
    }
}