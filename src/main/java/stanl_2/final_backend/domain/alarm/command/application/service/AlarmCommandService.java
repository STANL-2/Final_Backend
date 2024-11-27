package stanl_2.final_backend.domain.alarm.command.application.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import stanl_2.final_backend.domain.alarm.command.application.dto.AlarmRegistDTO;
import stanl_2.final_backend.domain.alarm.command.domain.aggregate.entity.Alarm;
import stanl_2.final_backend.domain.notices.command.application.dto.NoticeAlarmDTO;

public interface AlarmCommandService {
    SseEmitter subscribe(AlarmRegistDTO alarmRegistDTO, HttpServletResponse response);

    void sendToClient(SseEmitter emitter, String emitterId, Object data);

    void send(String memberLoginId, String message, String redirectUrl, String type, String createdAt);

    Alarm createAlarm(String memberId, String message, String redirectUrl, String type, String createdAt);

    void sendNoticeAlarm(NoticeAlarmDTO noticeAlarmDTO);

    Boolean updateReadStatus(String alarmId);
}
