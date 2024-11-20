package stanl_2.final_backend.domain.sales_history.query.controller;

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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.sales_history.common.response.SalesHistoryResponseMessage;
import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistorySearchDTO;
import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistorySelectDTO;
import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistoryStatisticsDTO;
import stanl_2.final_backend.domain.sales_history.query.service.SalesHistoryQueryService;

import java.security.Principal;
import java.util.Map;

@RestController(value = "querySalesHistoryController")
@RequestMapping("/api/v1/salesHistory")
public class SalesHistoryController {
    private final SalesHistoryQueryService salesHistoryQueryService;

    @Autowired
    public SalesHistoryController(SalesHistoryQueryService salesHistoryQueryService) {
        this.salesHistoryQueryService = salesHistoryQueryService;
    }

    /* 설명.
    *  - 사원 -> 일주일별, 월별, 연별(조회기간별)

        본인 판매내역 조회 0

        본인 판매내역 상세 0

        본인 실적,수당, 매출액 합 0

        본인 검색
    * */

    @Operation(summary = "사원 판매내역 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SalesHistoryResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/employee")
    public ResponseEntity<SalesHistoryResponseMessage> getAllSalesHistoryByEmployee(Principal principal,
                                                                                    @PageableDefault(size = 20) Pageable pageable){
        SalesHistorySelectDTO salesHistorySelectDTO = new SalesHistorySelectDTO();

        salesHistorySelectDTO.setSearcherName(principal.getName());

        Page<SalesHistorySelectDTO> responseSalesHistory = salesHistoryQueryService.selectAllSalesHistoryByEmployee(salesHistorySelectDTO, pageable);

        return ResponseEntity.ok(SalesHistoryResponseMessage.builder()
                .httpStatus(200)
                .msg("사원 판매내역 조회 성공")
                .result(responseSalesHistory)
                .build());
    }

    @Operation(summary = "판매내역 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SalesHistoryResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("")
    public ResponseEntity<SalesHistoryResponseMessage> getAllSalesHistory(@PageableDefault(size = 20) Pageable pageable){

        Page<SalesHistorySelectDTO> responseSalesHistory = salesHistoryQueryService.selectAllSalesHistory(pageable);

        return ResponseEntity.ok(SalesHistoryResponseMessage.builder()
                .httpStatus(200)
                .msg("판매내역 조회 성공")
                .result(responseSalesHistory)
                .build());
    }

    @Operation(summary = "판매내역 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SalesHistoryResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("employee/{salesHistoryId}")
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

        SalesHistoryStatisticsDTO responseSalesHistory = salesHistoryQueryService.selectStatisticsSearchByEmployee(salesHistorySearchDTO);

        return ResponseEntity.ok(SalesHistoryResponseMessage.builder()
                .httpStatus(200)
                .msg("사원 통계(실적,수당,매출액) 검색 성공")
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

        SalesHistoryStatisticsDTO responseSalesHistory = salesHistoryQueryService.selectStatisticsSearchMonthByEmployee(salesHistorySearchDTO);

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

        SalesHistoryStatisticsDTO responseSalesHistory = salesHistoryQueryService.selectStatisticsSearchYearBb yEmployee(salesHistorySearchDTO);

        return ResponseEntity.ok(SalesHistoryResponseMessage.builder()
                .httpStatus(200)
                .msg("사원 통계(실적,수당,매출액) 월 별 검색 성공")
                .result(responseSalesHistory)
                .build());
    }

}
