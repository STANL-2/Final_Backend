package stanl_2.final_backend.domain.sales_history.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.product.common.response.ProductResponseMessage;
import stanl_2.final_backend.domain.sales_history.common.response.SalesHistoryResponseMessage;
import stanl_2.final_backend.domain.sales_history.query.dto.*;
import stanl_2.final_backend.domain.sales_history.query.service.SalesHistoryQueryService;

import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController(value = "querySalesHistoryController")
@RequestMapping("/api/v1/salesHistory")
public class SalesHistoryController {
    private final SalesHistoryQueryService salesHistoryQueryService;

    @Autowired
    public SalesHistoryController(SalesHistoryQueryService salesHistoryQueryService) {
        this.salesHistoryQueryService = salesHistoryQueryService;
    }

    @Operation(summary = "판매내역 조회(사원, 관리자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SalesHistoryResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/employee")
    public ResponseEntity<SalesHistoryResponseMessage> getAllSalesHistoryByEmployee(Principal principal,
                                                                                    @PageableDefault(size = 20) Pageable pageable,
                                                                                    @RequestParam(required = false) String sortField,
                                                                                    @RequestParam(required = false) String sortOrder){
        SalesHistorySelectDTO salesHistorySelectDTO = new SalesHistorySelectDTO();

        salesHistorySelectDTO.setSearcherName(principal.getName());

        if (sortField != null && sortOrder != null) {
            Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, sortField));
        }

        Page<SalesHistorySelectDTO> responseSalesHistory = salesHistoryQueryService.selectAllSalesHistoryByEmployee(salesHistorySelectDTO, pageable);

        return ResponseEntity.ok(SalesHistoryResponseMessage.builder()
                .httpStatus(200)
                .msg("사원 판매내역 조회 성공")
                .result(responseSalesHistory)
                .build());
    }

    @Operation(summary = "판매내역 조회(관리자, 담당자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SalesHistoryResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("")
    public ResponseEntity<SalesHistoryResponseMessage> getAllSalesHistory(@PageableDefault(size = 20) Pageable pageable,
                                                                          @RequestParam(required = false) String sortField,
                                                                          @RequestParam(required = false) String sortOrder){

        if (sortField != null && sortOrder != null) {
            Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, sortField));
        }

        Page<SalesHistorySelectDTO> responseSalesHistory = salesHistoryQueryService.selectAllSalesHistory(pageable);

        return ResponseEntity.ok(SalesHistoryResponseMessage.builder()
                .httpStatus(200)
                .msg("판매내역 조회 성공")
                .result(responseSalesHistory)
                .build());
    }

    @Operation(summary = "판매내역 상세 조회(전체)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SalesHistoryResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("{salesHistoryId}")
    public ResponseEntity<SalesHistoryResponseMessage> getSalesHistoryDetail(@PathVariable("salesHistoryId") String salesHistoryId){

        SalesHistorySelectDTO salesHistorySelectDTO = new SalesHistorySelectDTO();

        salesHistorySelectDTO.setSalesHistoryId(salesHistoryId);

        SalesHistorySelectDTO responseSalesHistory = salesHistoryQueryService.selectSalesHistoryDetail(salesHistorySelectDTO);

        return ResponseEntity.ok(SalesHistoryResponseMessage.builder()
                .httpStatus(200)
                .msg("판매내역 상세 조회 성공")
                .result(responseSalesHistory)
                .build());
    }

    @Operation(summary = "판매내역 조회기간별 검색(사원)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SalesHistoryResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/employee/search")
    public ResponseEntity<SalesHistoryResponseMessage> getSalesHistorySearchByEmployee(@RequestBody SalesHistorySearchDTO salesHistorySearchDTO
                                                                                           ,Principal principal,
                                                                                    @PageableDefault(size = 20) Pageable pageable,
                                                                                       @RequestParam(required = false) String sortField,
                                                                                       @RequestParam(required = false) String sortOrder){

        salesHistorySearchDTO.setSearcherName(principal.getName());


        if (sortField != null && sortOrder != null) {
            Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, sortField));
        }
        Page<SalesHistorySelectDTO> responseSalesHistory = salesHistoryQueryService.selectSalesHistorySearchByEmployee(salesHistorySearchDTO, pageable);

        return ResponseEntity.ok(SalesHistoryResponseMessage.builder()
                .httpStatus(200)
                .msg("판매내역 조회기간별 검색(사원) 성공")
                .result(responseSalesHistory)
                .build());
    }

    @Operation(summary = "판매내역 조회기간별 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SalesHistoryResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/search")
    public ResponseEntity<SalesHistoryResponseMessage> getSalesHistoryBySearch(@RequestBody SalesHistorySearchDTO salesHistorySearchDTO,
                                                                                       @PageableDefault(size = 20) Pageable pageable,
                                                                               @RequestParam(required = false) String sortField,
                                                                               @RequestParam(required = false) String sortOrder) throws GeneralSecurityException {


        if (sortField != null && sortOrder != null) {
            Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, sortField));
        }
        Page<SalesHistorySelectDTO> responseSalesHistory = salesHistoryQueryService.selectSalesHistoryBySearch(salesHistorySearchDTO, pageable);

        return ResponseEntity.ok(SalesHistoryResponseMessage.builder()
                .httpStatus(200)
                .msg("판매내역 조회기간별 검색 성공")
                .result(responseSalesHistory)
                .build());
    }


    @Operation(summary = "사원 통계(실적,수당,매출액) 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SalesHistoryResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("employee/statistics")
    public ResponseEntity<SalesHistoryResponseMessage> getStatisticsByEmployee(Principal principal){

        SalesHistorySelectDTO salesHistorySelectDTO = new SalesHistorySelectDTO();

        salesHistorySelectDTO.setSearcherName(principal.getName());

        SalesHistoryStatisticsDTO responseSalesHistory = salesHistoryQueryService.selectStatisticsByEmployee(salesHistorySelectDTO);

        return ResponseEntity.ok(SalesHistoryResponseMessage.builder()
                .httpStatus(200)
                .msg("사원 통계(실적,수당,매출액) 조회 성공")
                .result(responseSalesHistory)
                .build());
    }

    @Operation(summary = "사원 통계(실적,수당,매출액) 조회기간별 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SalesHistoryResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("employee/statistics/search")
    public ResponseEntity<SalesHistoryResponseMessage> getStatisticsSearchByEmployee(@RequestParam Map<String, String> params
                                                                                    ,Principal principal,
                                                                                     @PageableDefault(size = 20) Pageable pageable){

        SalesHistorySearchDTO salesHistorySearchDTO = new SalesHistorySearchDTO();

        salesHistorySearchDTO.setSearcherName(principal.getName());
        salesHistorySearchDTO.setStartDate(params.get("startDate"));
        salesHistorySearchDTO.setEndDate(params.get("endDate"));
        salesHistorySearchDTO.setPeriod(params.get("period"));

        SalesHistoryStatisticsDTO responseSalesHistory = salesHistoryQueryService.selectStatisticsSearchByEmployee(salesHistorySearchDTO);

        return ResponseEntity.ok(SalesHistoryResponseMessage.builder()
                .httpStatus(200)
                .msg("사원 통계(실적,수당,매출액) 검색 성공")
                .result(responseSalesHistory)
                .build());
    }

    @Operation(summary = "사원 통계(실적,수당,매출액) 일자별 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SalesHistoryResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("employee/statistics/search/daily")
    public ResponseEntity<SalesHistoryResponseMessage> getStatisticsSearchByEmployeeDaily(@RequestParam Map<String, String> params
            ,Principal principal,
                                                                                     @PageableDefault(size = 20) Pageable pageable){

        SalesHistorySearchDTO salesHistorySearchDTO = new SalesHistorySearchDTO();

        salesHistorySearchDTO.setSearcherName(principal.getName());
        salesHistorySearchDTO.setStartDate(params.get("startDate"));
        salesHistorySearchDTO.setEndDate(params.get("endDate"));
        salesHistorySearchDTO.setPeriod(params.get("period"));

        List<SalesHistoryStatisticsDTO> responseSalesHistory = salesHistoryQueryService.selectStatisticsSearchByEmployeeDaily(salesHistorySearchDTO);

        return ResponseEntity.ok(SalesHistoryResponseMessage.builder()
                .httpStatus(200)
                .msg("사원 통계(실적,수당,매출액) 일자별 검색 성공")
                .result(responseSalesHistory)
                .build());
    }

    @Operation(summary = "본인 통계(실적,수당,매출액) 조회기간별 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SalesHistoryResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("statistics/mySearch")
    public ResponseEntity<SalesHistoryResponseMessage> getMyStatisticsBySearch(@RequestBody SalesHistoryRankedDataDTO salesHistoryRankedDataDTO,
                                                                               @PageableDefault(size = 20) Pageable pageable,
                                                                               Principal principal){



//        salesHistoryRankedDataDTO.setMemberList(memberList);
        salesHistoryRankedDataDTO.setMemberId(principal.getName());

        Page<SalesHistoryRankedDataDTO> responseSalesHistory = salesHistoryQueryService.selectMyStatisticsBySearch(salesHistoryRankedDataDTO,pageable);

        return ResponseEntity.ok(SalesHistoryResponseMessage.builder()
                .httpStatus(200)
                .msg("본인 통계(실적,수당,매출액) 검색 성공")
                .result(responseSalesHistory)
                .build());
    }


    @Operation(summary = "사원 통계(실적,수당,매출액) 월 별 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SalesHistoryResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("employee/statistics/search/month")
    public ResponseEntity<SalesHistoryResponseMessage> getStatisticsSearchMonthByEmployee(@RequestParam Map<String, String> params
                                                                                    ,Principal principal,
                                                                                     @PageableDefault(size = 20) Pageable pageable){

        SalesHistorySearchDTO salesHistorySearchDTO = new SalesHistorySearchDTO();

        /* 설명. 2024-07 식으로 와야함 !!!!! */
        salesHistorySearchDTO.setSearcherName(principal.getName());
        salesHistorySearchDTO.setStartDate(params.get("startDate"));
        salesHistorySearchDTO.setEndDate(params.get("endDate"));

        List<SalesHistoryStatisticsDTO> responseSalesHistory = salesHistoryQueryService.selectStatisticsSearchMonthByEmployee(salesHistorySearchDTO);

        return ResponseEntity.ok(SalesHistoryResponseMessage.builder()
                .httpStatus(200)
                .msg("사원 통계(실적,수당,매출액) 월 별 검색 성공")
                .result(responseSalesHistory)
                .build());
    }

    @Operation(summary = "사원 통계(실적,수당,매출액) 연도 별 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SalesHistoryResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("employee/statistics/search/year")
    public ResponseEntity<SalesHistoryResponseMessage> getStatisticsSearchYearByEmployee(@RequestParam Map<String, String> params
            ,Principal principal,
                                                                                          @PageableDefault(size = 20) Pageable pageable){

        SalesHistorySearchDTO salesHistorySearchDTO = new SalesHistorySearchDTO();

        /* 설명. 2024 식으로 와야함 !!!!! */
        salesHistorySearchDTO.setSearcherName(principal.getName());
        salesHistorySearchDTO.setStartDate(params.get("startDate"));
        salesHistorySearchDTO.setEndDate(params.get("endDate"));

        List<SalesHistoryStatisticsDTO> responseSalesHistory = salesHistoryQueryService.selectStatisticsSearchYearByEmployee(salesHistorySearchDTO);

        return ResponseEntity.ok(SalesHistoryResponseMessage.builder()
                .httpStatus(200)
                .msg("사원 통계(실적,수당,매출액) 연도 별 검색 성공")
                .result(responseSalesHistory)
                .build());
    }

    @Operation(summary = "사원 별 통계(실적,수당,매출액) 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SalesHistoryResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("statistics")
    public ResponseEntity<SalesHistoryResponseMessage> getStatistics(@RequestBody SalesHistoryRankedDataDTO salesHistoryRankedDataDTO,
                                                              @PageableDefault(size = 20) Pageable pageable){


        Page<SalesHistoryRankedDataDTO> responseSalesHistory = salesHistoryQueryService.selectStatistics(salesHistoryRankedDataDTO, pageable);

        return ResponseEntity.ok(SalesHistoryResponseMessage.builder()
                .httpStatus(200)
                .msg("사원 별 통계(실적,수당,매출액) 성공")
                .result(responseSalesHistory)
                .build());
    }

    @Operation(summary = "사원 별 통계(실적,수당,매출액) 조회기간별 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SalesHistoryResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("statistics/search")
    public ResponseEntity<SalesHistoryResponseMessage> getStatisticsBySearch(@RequestBody SalesHistoryRankedDataDTO salesHistoryRankedDataDTO,
                                                              @PageableDefault(size = 20) Pageable pageable){

        Page<SalesHistoryRankedDataDTO> responseSalesHistory = salesHistoryQueryService.selectStatisticsBySearch(salesHistoryRankedDataDTO,pageable);

        return ResponseEntity.ok(SalesHistoryResponseMessage.builder()
                .httpStatus(200)
                .msg("사원 별 통계(실적,수당,매출액) 검색 성공")
                .result(responseSalesHistory)
                .build());
    }

    @Operation(summary = "사원 별 통계(실적,수당,매출액) 조회기간별 평균 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SalesHistoryResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("statistics/average")
    public ResponseEntity<SalesHistoryResponseMessage> getStatisticsEmployeeAverageBySearch(@RequestBody SalesHistoryRankedDataDTO salesHistoryRankedDataDTO,
                                                                             @PageableDefault(size = 20) Pageable pageable){

        Page<SalesHistoryStatisticsAverageDTO> responseSalesHistory = salesHistoryQueryService.selectStatisticsAverageBySearch(salesHistoryRankedDataDTO,pageable);

        return ResponseEntity.ok(SalesHistoryResponseMessage.builder()
                .httpStatus(200)
                .msg("사원 별 통계(실적,수당,매출액) 조회기간별 평균 조회 성공")
                .result(responseSalesHistory)
                .build());
    }

    @Operation(summary = "판매내역 엑셀 다운")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "판매내역 엑셀 다운 성공",
                    content = {@Content(schema = @Schema(implementation = SalesHistoryResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/excel")
    public void exporSalesHistory(HttpServletResponse response){

        salesHistoryQueryService.exportSalesHistoryToExcel(response);
    }

    @Operation(summary = "통계(실적,수당,매출액) 최대값 조회기간 별 검색(관리자, 담당자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SalesHistoryResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("statistics/best")
    public ResponseEntity<SalesHistoryResponseMessage> getStatisticsBestBySearch(@RequestBody SalesHistoryRankedDataDTO salesHistoryRankedDataDTO,
                                                                                @PageableDefault(size = 20) Pageable pageable){



        Page<SalesHistoryRankedDataDTO> responseSalesHistory = salesHistoryQueryService.selectStatisticsBestBySearch(salesHistoryRankedDataDTO, pageable);
        return ResponseEntity.ok(SalesHistoryResponseMessage.builder()
                .httpStatus(200)
                .msg("통계(실적,수당,매출액) 최대값 조회기간 별 검색(관리자, 담당자)")
                .result(responseSalesHistory)
                .build());
    }
}
