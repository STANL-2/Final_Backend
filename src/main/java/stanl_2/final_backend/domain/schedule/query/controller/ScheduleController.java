package stanl_2.final_backend.domain.schedule.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.schedule.common.response.ResponseMessage;
import stanl_2.final_backend.domain.schedule.query.dto.ScheduleDTO;
import stanl_2.final_backend.domain.schedule.query.dto.ScheduleYearMonthDTO;
import stanl_2.final_backend.domain.schedule.query.service.ScheduleService;

import java.util.List;

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
    @GetMapping("{memberId}")
    public ResponseEntity<ResponseMessage> selectAllSchedule(@PathVariable("memberId") String memberId){

        List<ScheduleDTO> schedules = scheduleService.selectAllSchedule(memberId);

        return ResponseEntity.ok(ResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(schedules)
                .build());
    }


    @Operation(summary = "일정 조건별(년&일) 전체 조회 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일정 조건별(년&일) 전체 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseMessage.class))})
    })
    @GetMapping("{memberId}/{year}/{month}")
    public ResponseEntity<ResponseMessage> selectMonthSchedule(@PathVariable("memberId") String memberId,
                                                               @PathVariable("year") String year,
                                                               @PathVariable("month") String month){

        ScheduleYearMonthDTO scheduleYearMonthDTO = new ScheduleYearMonthDTO();
        scheduleYearMonthDTO.setMemberId(memberId);
        scheduleYearMonthDTO.setYear(year);
        scheduleYearMonthDTO.setMonth(month);

        List<ScheduleYearMonthDTO> yearMonthSchedule = scheduleService.selectYearMonthSchedule(scheduleYearMonthDTO);

        return ResponseEntity.ok(ResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(yearMonthSchedule)
                .build());
    }


    @Operation(summary = "일정 상세 조회 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일정 상세 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseMessage.class))})
    })
    @GetMapping("{memberId}/{scheduleId}")
    public ResponseEntity<ResponseMessage> selectDetailSchedule(@PathVariable("memberId") String memberId,
                                                                @PathVariable("scheduleId") String scheduleId){

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setMemberId(memberId);
        scheduleDTO.setId(scheduleId);

        ScheduleDTO responseSchedule = scheduleService.selectDetailSchedule(scheduleDTO);

        return ResponseEntity.ok(ResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(responseSchedule)
                .build());
    }
}
