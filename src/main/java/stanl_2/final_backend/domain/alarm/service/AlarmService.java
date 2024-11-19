package stanl_2.final_backend.domain.alarm.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import stanl_2.final_backend.domain.alarm.aggregate.dto.AlarmDTO;
import stanl_2.final_backend.domain.alarm.aggregate.entity.Alarm;

public interface AlarmService {
    SseEmitter subscribe(AlarmDTO alarmDTO, HttpServletResponse response);

    void sendToClient(SseEmitter emitter, String emitterId, Object data);

    void send(String memberLoginId, String message, String redirectUrl, String type, String createdAt);

    Alarm createAlarm(String memberId, String message, String redirectUrl, String type, String createdAt);
}
