package stanl_2.final_backend.domain.log.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.A_sample.common.response.SampleResponseMessage;
import stanl_2.final_backend.domain.log.common.response.LogResponseMessage;
import stanl_2.final_backend.domain.log.query.dto.LogDTO;
import stanl_2.final_backend.domain.log.query.service.LogQueryService;

import java.util.List;

@RestController(value = "queryLogController")
@RequestMapping("/api/v1/log")
public class LogController {

    private final LogQueryService logQueryService;

    @Autowired
    public LogController(LogQueryService logQueryService) {
        this.logQueryService = logQueryService;
    }

    @Operation(summary = "로그 조회(시스템 관리자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SampleResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("")
    public ResponseEntity<LogResponseMessage> getLog(){

        List<LogDTO> log = logQueryService.selectLog();

        return ResponseEntity.ok(LogResponseMessage.builder()
                                                    .httpStatus(200)
                                                    .result(log)
                                                    .build());
    }
}
