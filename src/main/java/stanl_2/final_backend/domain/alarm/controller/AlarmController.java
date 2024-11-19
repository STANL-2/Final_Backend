package stanl_2.final_backend.domain.alarm.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import stanl_2.final_backend.domain.alarm.aggregate.dto.AlarmDTO;
import stanl_2.final_backend.domain.alarm.service.AlarmService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/alarm")
public class AlarmController {

    private final AlarmService alarmService;

    @Autowired
    public AlarmController(AlarmService alarmService) {
        this.alarmService = alarmService;
    }

    @GetMapping(value= "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> subscribe(Principal principal,
                                                @RequestHeader(value = "Last-Event-ID", required = false,
                                                              defaultValue = "") String lastEventId,
                                                HttpServletResponse response){

        String memberLoginId = principal.getName();

        AlarmDTO alarmDTO = new AlarmDTO();
        alarmDTO.setMemberLoginId(memberLoginId);
        alarmDTO.setLastEventId(lastEventId);

        return ResponseEntity.ok(alarmService.subscribe(alarmDTO, response));
    }

}
