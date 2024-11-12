package stanl_2.final_backend.domain.schedule.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.schedule.command.application.dto.request.ScheduleModifyRequestDTO;
import stanl_2.final_backend.domain.schedule.command.application.dto.request.ScheduleRegistRequestDTO;
import stanl_2.final_backend.domain.schedule.command.application.service.ScheduleCommandService;
import stanl_2.final_backend.domain.schedule.common.response.ResponseMessage;

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
                    content = {@Content(schema = @Schema(implementation = ResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<ResponseMessage> registSchedule(@RequestBody ScheduleRegistRequestDTO scheduleRegistRequestDTO){

        Boolean answer = scheduleCommandService.registSchedule(scheduleRegistRequestDTO);

        return ResponseEntity.ok(ResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(answer)
                .build());
    }


    @Operation(summary = "일정 수정 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일정 수정 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseMessage.class))})
    })
    @PutMapping("{id}")
    public ResponseEntity<ResponseMessage> modifySchedule(@PathVariable String id,
                                                          @RequestBody ScheduleModifyRequestDTO scheduleModifyRequestDTO){

        scheduleModifyRequestDTO.setId(id);
        Boolean answer = scheduleCommandService.modifySchedule(scheduleModifyRequestDTO);

        return ResponseEntity.ok(ResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(answer)
                .build());
    }

    @Operation(summary = "일정 삭제 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일정 삭제 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseMessage.class))})
    })
    @DeleteMapping("{id}")
    public ResponseEntity<ResponseMessage> deleteSchedule(@PathVariable String id){

        Boolean answer = scheduleCommandService.deleteSchedule(id);

        return ResponseEntity.ok(ResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(answer)
                .build());
    }
}
