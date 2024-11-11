package stanl_2.final_backend.domain.schedule.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.schedule.common.response.ResponseMessage;
import stanl_2.final_backend.domain.schedule.query.dto.ScheduleDTO;
import stanl_2.final_backend.domain.schedule.query.service.ScheduleService;

@RestController("queryScheduleController")
@RequestMapping("/api/v1/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Operation(summary = "일정 전체 조회 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일정 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseMessage.class))})
    })
    @GetMapping("")
    public ResponseEntity<ResponseMessage> selectAllSchedule(@PathVariable String id){

        ScheduleDTO scheduleDTO = scheduleService.selectAllSchedule(id);

        return ResponseEntity.ok(ResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(scheduleDTO)
                .build());
    }
}
