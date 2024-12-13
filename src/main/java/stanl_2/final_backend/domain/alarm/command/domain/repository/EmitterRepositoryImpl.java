package stanl_2.final_backend.domain.alarm.command.domain.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class EmitterRepositoryImpl implements EmitterRepository{

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, Object> eventCache = new ConcurrentHashMap<>();

    @Override
    public SseEmitter save(String emitterId, SseEmitter sseEmitter) {

        emitters.put(emitterId, sseEmitter);
        return sseEmitter;
    }

    @Override
    public void deleteAllByEmitterId(String emitterId) {
        emitters.forEach((key, emitter) -> {
            if (key.startsWith(emitterId)) {
                emitters.remove(key);
            }
        });
    }

    @Override
    public Map<String, SseEmitter> findAllEmitterStartWithByMemberId(String memberId) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(memberId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<String, Object> findAllEventCacheStartWithByMemberId(String memberId) {
        return eventCache.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(memberId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void saveEventCache(String eventCacheId, Object event) {
        eventCache.put(eventCacheId, event);
    }

    @Override
    public void deleteAllEmitterStartWithMemberId(String memberId) {
        emitters.forEach(
                (key, emitter) -> {
                    if (key.startsWith(memberId)){
                        emitters.remove(key);
                    }
                }
        );
    }

    @Override
    public void deleteAllEventCacheStartWithmemberId(String memberId) {
        eventCache.forEach(
                (key, emitter) -> {
                    if (key.startsWith(memberId)){
                        eventCache.remove(key);
                    }
                }
        );
    }

    @Override
    public SseEmitter findEmitterByMemberId(String memberId) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(memberId)) // memberId로 시작하는 키 검색
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null); // 없으면 null 반환
    }
}
