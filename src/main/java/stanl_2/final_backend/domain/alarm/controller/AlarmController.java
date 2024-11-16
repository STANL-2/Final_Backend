package stanl_2.final_backend.domain.alarm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import stanl_2.final_backend.domain.alarm.aggregate.dto.AlarmDTO;
import stanl_2.final_backend.domain.alarm.service.AlarmService;

@RestController("queryAlarmController")
@RequestMapping("/api/v1/alarm")
public class AlarmController {

    private final AlarmService alarmService;

    @Autowired
    public AlarmController(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @GetMapping(value= "/connect/{memberId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> subscribe(@PathVariable String memberId,
                                         @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "")
                                         String lastEventId){

        AlarmDTO alarmDTO = new AlarmDTO();
        alarmDTO.setMemberId(memberId);
        alarmDTO.setLastEventId(lastEventId);

        return ResponseEntity.ok(alarmService.subscribe(alarmDTO));
    }

}
