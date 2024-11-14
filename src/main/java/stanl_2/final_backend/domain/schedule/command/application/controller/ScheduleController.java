package stanl_2.final_backend.domain.schedule.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.schedule.command.application.dto.ScheduleModifyDTO;
import stanl_2.final_backend.domain.schedule.command.application.dto.ScheduleRegistDTO;
import stanl_2.final_backend.domain.schedule.command.application.service.ScheduleCommandService;
import stanl_2.final_backend.domain.schedule.common.response.ScheduleResponseMessage;

@RestController("commandScheduleController")
@RequestMapping("/api/v1/schedule")
public class ScheduleController {

    private final ScheduleCommandService scheduleCommandService;

    @Autowired
    public ScheduleController(ScheduleCommandService scheduleCommandService) {
        this.scheduleCommandService = scheduleCommandService;
    }

    @Operation(summary = "일정 등록 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일정 등록 성공",
                    content = {@Content(schema = @Schema(implementation = ScheduleResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<ScheduleResponseMessage> registSchedule(@RequestBody ScheduleRegistDTO scheduleRegistDTO){

        Boolean answer = scheduleCommandService.registSchedule(scheduleRegistDTO);

        return ResponseEntity.ok(ScheduleResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(answer)
                .build());
    }


    @Operation(summary = "일정 수정 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일정 수정 성공",
                    content = {@Content(schema = @Schema(implementation = ScheduleResponseMessage.class))})
    })
    @PutMapping("{scheduleId}")
    public ResponseEntity<ScheduleResponseMessage> modifySchedule(@PathVariable String scheduleId,
                                                                  @RequestBody ScheduleModifyDTO scheduleModifyDTO){

        scheduleModifyDTO.setId(scheduleId);
        Boolean answer = scheduleCommandService.modifySchedule(scheduleModifyDTO);

        return ResponseEntity.ok(ScheduleResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(answer)
                .build());
    }

    @Operation(summary = "일정 삭제 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일정 삭제 성공",
                    content = {@Content(schema = @Schema(implementation = ScheduleResponseMessage.class))})
    })
    @DeleteMapping("{scheduleId}")
    public ResponseEntity<ScheduleResponseMessage> deleteSchedule(@PathVariable String scheduleId){

        Boolean answer = scheduleCommandService.deleteSchedule(scheduleId);

        return ResponseEntity.ok(ScheduleResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(answer)
                .build());
    }
}
