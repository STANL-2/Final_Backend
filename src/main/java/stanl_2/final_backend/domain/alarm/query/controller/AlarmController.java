package stanl_2.final_backend.domain.alarm.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.alarm.common.response.AlarmResponseMessage;
import stanl_2.final_backend.domain.alarm.query.dto.CursorDTO;
import stanl_2.final_backend.domain.alarm.query.service.AlarmQueryService;
import stanl_2.final_backend.domain.schedule.common.response.ScheduleResponseMessage;

import java.security.Principal;

@RestController("queryAlarmController")
@RequestMapping("/api/v1/alarm")
public class AlarmController {

    private final AlarmQueryService alarmQueryService;

    @Autowired
    public AlarmController(AlarmQueryService alarmQueryService) {
        this.alarmQueryService = alarmQueryService;
    }

    @Operation(summary = "고객 알림창 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "고객 알림창 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ScheduleResponseMessage.class))})
    })
    @GetMapping("")
    public ResponseEntity<AlarmResponseMessage> readMemberAlarms(Principal principal,
                                                     @RequestParam(value = "cursor", required = false) Long cursorId){

        String memberLoginId = principal.getName();
        Integer size = 3;

        CursorDTO cursorDTO = new CursorDTO();
        cursorDTO.setCursorId(cursorId);
        cursorDTO.setSize(size);

        CursorDTO alarmResponseDTO = alarmQueryService.readMemberAlarms(cursorDTO, memberLoginId);

        return ResponseEntity.ok(AlarmResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(alarmResponseDTO)
                .build());
    }
}
