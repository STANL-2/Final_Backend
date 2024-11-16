package stanl_2.final_backend.domain.alarm.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import stanl_2.final_backend.domain.alarm.aggregate.dto.AlarmDTO;

public interface AlarmService {
    SseEmitter subscribe(AlarmDTO alarmDTO);
}
