package stanl_2.final_backend.domain.log.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.customer.common.response.CustomerResponseMessage;
import stanl_2.final_backend.domain.log.common.response.LogResponseMessage;
import stanl_2.final_backend.domain.log.query.dto.LogDTO;
import stanl_2.final_backend.domain.log.query.dto.LogSearchDTO;
import stanl_2.final_backend.domain.log.query.service.LogQueryService;

@Slf4j
@RestController(value = "queryLogController")
@RequestMapping("/api/v1/log")
public class LogController {

    private final LogQueryService logQueryService;

    @Autowired
    public LogController(LogQueryService logQueryService) {
        this.logQueryService = logQueryService;
    }

    @Operation(summary = "로그 검색 조회(시스템 관리자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = LogResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("")
    public ResponseEntity<LogResponseMessage> getLogs(
            @RequestParam(required = false) String logId,
            @RequestParam(required = false) String loginId,
            @RequestParam(required = false) String ipAddress,
            @RequestParam(required = false) String requestTime_start,
            @RequestParam(required = false) String requestTime_end,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String method,
            @RequestParam(required = false) String uri,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortOrder,
            @PageableDefault(size = 10) Pageable pageable
    ){

        // 정렬 추가
        if (sortField != null && sortOrder != null) {
            Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, sortField));
        }


        LogSearchDTO searchLogDTO = new LogSearchDTO(logId, loginId, ipAddress, requestTime_start, requestTime_end, status, method, uri);
        Page<LogDTO> logDTOPage = logQueryService.selectLogs(pageable, searchLogDTO);

        return ResponseEntity.ok(LogResponseMessage.builder()
                                                    .httpStatus(200)
                                                    .result(logDTOPage)
                                                    .build());
    }

    @Operation(summary = "엑셀 다운로드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = CustomerResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("excel")
    public void exportCustomer(HttpServletResponse response){

        logQueryService.exportLogToExcel(response);
    }
}
