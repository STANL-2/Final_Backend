package stanl_2.final_backend.domain.alarm.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import stanl_2.final_backend.domain.alarm.command.application.dto.AlarmRegistDTO;
import stanl_2.final_backend.domain.alarm.command.application.service.AlarmCommandService;
import stanl_2.final_backend.domain.alarm.common.response.AlarmResponseMessage;
import stanl_2.final_backend.domain.schedule.common.response.ScheduleResponseMessage;

import java.security.Principal;

@RestController("commandAlarmController")
@RequestMapping("/api/v1/alarm")
public class AlarmController {

    private final AlarmCommandService alarmCommandService;

    @Autowired
    public AlarmController(AlarmCommandService alarmCommandService) {
        this.alarmCommandService = alarmCommandService;
    }

    @Operation(summary = "sse 연결")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sse 연결 성공",
                    content = {@Content(schema = @Schema(implementation = ScheduleResponseMessage.class))})
    })
    @GetMapping(value= "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> subscribe(Principal principal,
                                                @RequestHeader(value = "Last-Event-ID", required = false,
                                                              defaultValue = "") String lastEventId,
                                                HttpServletResponse response){

        String memberLoginId = principal.getName();

        AlarmRegistDTO alarmRegistDTO = new AlarmRegistDTO();
        alarmRegistDTO.setMemberLoginId(memberLoginId);
        alarmRegistDTO.setLastEventId(lastEventId);

        return ResponseEntity.ok(alarmCommandService.subscribe(alarmRegistDTO, response));
    }


    @Operation(summary = "회원 알림 읽음 처리")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 알림 읽음 처리 완료",
                    content = {@Content(schema = @Schema(implementation = ScheduleResponseMessage.class))})
    })
    @PutMapping("{alarmId}")
    public ResponseEntity<AlarmResponseMessage> updateReadStatus(@PathVariable String alarmId){

        Boolean answer = alarmCommandService.updateReadStatus(alarmId);

        return ResponseEntity.ok(AlarmResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(answer)
                .build());
    }
}
