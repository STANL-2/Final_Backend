package stanl_2.final_backend.domain.alarm.repository;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import stanl_2.final_backend.domain.alarm.aggregate.entity.Alarm;

import java.util.Map;

public interface EmitterRepository {
    SseEmitter save(String emitterId, SseEmitter sseEmitter);

    void deleteAllByEmitterId(String emitterId);

    Map<String,SseEmitter> findAllEmitterStartWithByMemberId(String memberId);

    Map<String, Object> findAllEventCacheStartWithByMemberId(String memberId);

    void saveEventCache(String eventCacheId, Object event);

    void deleteAllEmitterStartWithMemberId(String memberId);

    void deleteAllEventCacheStartWithmemberId(String memberId);
}
