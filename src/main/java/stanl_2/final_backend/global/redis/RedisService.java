package stanl_2.final_backend.global.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 키와 값을 저장하며 TTL 설정
    public void setKeyWithTTL(String key, Object value, long ttlInSeconds) {
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(ttlInSeconds));
    }

    // 키 조회
    public Object getKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // TTL 설정 (기존 키에 대해)
    public boolean setTTL(String key, long ttlInSeconds) {
        return redisTemplate.expire(key, Duration.ofSeconds(ttlInSeconds));
    }
}