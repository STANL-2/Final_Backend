package stanl_2.final_backend.domain.alarm.command.domain.repository;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface EmitterRepository {
    SseEmitter save(String emitterId, SseEmitter sseEmitter);

    void deleteAllByEmitterId(String emitterId);

    Map<String,SseEmitter> findAllEmitterStartWithByMemberId(String memberId);

    Map<String, Object> findAllEventCacheStartWithByMemberId(String memberId);

    void saveEventCache(String eventCacheId, Object event);

    void deleteAllEmitterStartWithMemberId(String memberId);

    void deleteAllEventCacheStartWithmemberId(String memberId);

    SseEmitter findEmitterByMemberId(String memberId);
}
