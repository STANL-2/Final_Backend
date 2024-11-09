package stanl_2.final_backend.domain.schedule.command.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.schedule.command.application.service.ScheduleService;
import stanl_2.final_backend.domain.schedule.common.response.ResponseMessage;

@RestController("commandScheduleController")
@RequestMapping("/api/v1/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("")
    public ResponseEntity<?> registSchedule(){



        return ResponseEntity.ok(new ResponseMessage(200,"성공",""));
    }

    @PutMapping("")
    public ResponseEntity<?> modifySchedule(){

        return ResponseEntity.ok(new ResponseMessage(200,"성공",""));
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteSchedule(){

        return ResponseEntity.ok(new ResponseMessage(200,"성공",""));
    }
}
