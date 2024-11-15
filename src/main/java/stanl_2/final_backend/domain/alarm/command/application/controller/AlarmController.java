package stanl_2.final_backend.domain.alarm.command.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.alarm.command.application.service.AlarmCommandService;
import stanl_2.final_backend.domain.alarm.common.response.AlarmResponseMessage;
import stanl_2.final_backend.domain.schedule.common.response.ScheduleResponseMessage;

@RestController("commandAlarmController")
@RequestMapping("/api/v1/alarm")
public class AlarmController {

    private final AlarmCommandService alarmCommandService;

    @Autowired
    public AlarmController(AlarmCommandService alarmCommandService) {
        this.alarmCommandService = alarmCommandService;
    }

    @PostMapping("")
    public ResponseEntity<AlarmResponseMessage> registAlarm(){

        return ResponseEntity.ok(AlarmResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(" ")
                .build());
    }
}
