package stanl_2.final_backend.domain.schedule.command.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.schedule.command.application.dto.request.ScheduleModifyRequestDTO;
import stanl_2.final_backend.domain.schedule.command.application.dto.request.ScheduleRegistRequestDTO;
import stanl_2.final_backend.domain.schedule.command.application.dto.response.ScheduleModifyResponseDTO;
import stanl_2.final_backend.domain.schedule.command.application.dto.response.ScheduleRegistResponseDTO;
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
    public ResponseEntity<ResponseMessage> registSchedule(@RequestBody ScheduleRegistRequestDTO scheduleRegistRequestDTO){

        ScheduleRegistResponseDTO scheduleRegistResponseDTO = scheduleService.registSchedule(scheduleRegistRequestDTO);

        return ResponseEntity.ok(new ResponseMessage(200,"성공",scheduleRegistResponseDTO));
    }

    @PutMapping("")
    public ResponseEntity<ResponseMessage> modifySchedule(@RequestBody ScheduleModifyRequestDTO scheduleModifyRequestDTO){

        ScheduleModifyResponseDTO scheduleModifyResponseDTO = scheduleService.modifySchedule(scheduleModifyRequestDTO);

        return ResponseEntity.ok(new ResponseMessage(200,"성공",scheduleModifyResponseDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseMessage> deleteSchedule(@PathVariable String id){

        Boolean active = scheduleService.deleteSchedule(id);

        return ResponseEntity.ok(new ResponseMessage(200,"성공",active));
    }
}
