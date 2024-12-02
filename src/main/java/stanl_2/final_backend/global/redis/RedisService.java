package stanl_2.final_backend.global.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;

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

    public void clearNoticeCache() {
        Set<String> keys = redisTemplate.keys("NoticeCache*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys); // Delete all matching keys
            System.out.println("Deleted NoticeCache keys: " + keys);
        } else {
            System.out.println("No keys found for pattern 'NoticeCache*'.");
        }
    }

    public void clearPromotionCache() {
        Set<String> keys = redisTemplate.keys("PromotionCache*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys); // Delete all matching keys
            System.out.println("Deleted PromotionCache keys: " + keys);
        } else {
            System.out.println("No keys found for pattern 'PromotionCache*'.");
        }
    }

    public void clearProblemCache() {
        Set<String> keys = redisTemplate.keys("ProblemCache*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys); // Delete all matching keys
            System.out.println("Deleted ProblemCache keys: " + keys);
        } else {
            System.out.println("No keys found for pattern 'ProblemCache*'.");
        }
    }

    public void clearMailCache(String key) {
        //
        System.out.println("1캐시 삭제!!");
        System.out.println(redisTemplate.delete(key));


    }

}