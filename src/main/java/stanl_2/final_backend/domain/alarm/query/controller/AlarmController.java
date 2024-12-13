package stanl_2.final_backend.domain.alarm.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.alarm.common.response.AlarmResponseMessage;
import stanl_2.final_backend.domain.alarm.query.dto.AlarmSelectDTO;
import stanl_2.final_backend.domain.alarm.query.dto.AlarmSelectReadDTO;
import stanl_2.final_backend.domain.alarm.query.dto.AlarmSelectTypeDTO;
import stanl_2.final_backend.domain.alarm.query.dto.AlarmSelectUnreadDTO;
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

    @Operation(summary = "회원 알림창 전체 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 알림창 전체 조회 완료",
                    content = {@Content(schema = @Schema(implementation = ScheduleResponseMessage.class))})
    })
    @GetMapping("")
    public ResponseEntity<AlarmResponseMessage> selectMemberAlarmType(Principal principal){

        String memberLoginId = principal.getName();

        AlarmSelectTypeDTO AlarmSelectTypeDTO = alarmQueryService.selectMemberByAlarmType(memberLoginId);

        return ResponseEntity.ok(AlarmResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(AlarmSelectTypeDTO)
                .build());
    }

    @Operation(summary = "회원 알림 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 알림 상세 조회 완료",
                    content = {@Content(schema = @Schema(implementation = ScheduleResponseMessage.class))})
    })
    @GetMapping("/{type}")
    public ResponseEntity<AlarmResponseMessage> selectReadAlarm(Principal principal,
                                                                @PathVariable String type,
                                                                @PageableDefault(size = 8) Pageable pageable){

        String memberLoginId = principal.getName();
        AlarmSelectDTO alarmSelectDTO = new AlarmSelectDTO();
        alarmSelectDTO.setMemberLoginId(memberLoginId);
        alarmSelectDTO.setType(type);

        Page<AlarmSelectDTO> allAlarms
                = alarmQueryService.selectAlarmByType(alarmSelectDTO , pageable);

        return ResponseEntity.ok(AlarmResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(allAlarms)
                .build());
    }
}
