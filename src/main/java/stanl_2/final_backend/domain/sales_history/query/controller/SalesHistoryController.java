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
import stanl_2.final_backend.domain.sales_history.query.dto.*;
import stanl_2.final_backend.domain.sales_history.query.service.SalesHistoryQueryService;

import java.security.Principal;
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

    /* 설명.
    *  - 사원 -> 일주일별, 월별, 연별(조회기간별)

        -> 사원 랭킹(윈도우 함수 이용)

*       default: 월별 판매내역 List
*
        1. (지역: 센터 검색 조회(NULL 혹은 '지역')

        2. (매장명: NULL 혹은 1)
        -1- NULL일 시 1번 결과를 다시 받는다. -> 그 행의 지역 검색결과(centerList)을 바탕으로 mem_id 조회
        -2- 1일 시 selectCenterById를 통해서 반환 값을 member에 where =cent_id)인 값 반환

        3. (이름:null 혹은 1)
        - null 일 시-2-의 리스트를 다시 받는다. -> select윈도우 함수 이용해서 멤버 결과 뿌려주기
        - 1 일시 하나의 멤버

        4. 분류에 따라서 뿌려줌
    * */


    /* 설명. todo
     *  1. (사원별, 매장별 순위) 각 실적, 수당, 매출액 별로 인자를 통해 동적 쿼리로 처리 가능한지 여부(내림차순)
     *  2. 판매내역 날짜별로(조회기간만), 매장별, 사원별
    * */
    @Operation(summary = "판매내역 조회(사원, 관리자)")
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

    @Operation(summary = "판매내역 조회(관리자, 담당자)")
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
    @GetMapping("/employee/search")
    public ResponseEntity<SalesHistoryResponseMessage> getSalesHistorySearchByEmployee(@RequestBody SalesHistorySearchDTO salesHistorySearchDTO
                                                                                           ,Principal principal,
                                                                                    @PageableDefault(size = 20) Pageable pageable){

        salesHistorySearchDTO.setSearcherName(principal.getName());

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
    @GetMapping("/search")
    public ResponseEntity<SalesHistoryResponseMessage> getSalesHistoryBySearch(@RequestBody SalesHistorySearchDTO salesHistorySearchDTO,
                                                                                       @PageableDefault(size = 20) Pageable pageable){

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

        SalesHistoryStatisticsDTO responseSalesHistory = salesHistoryQueryService.selectStatisticsSearchYearByEmployee(salesHistorySearchDTO);

        return ResponseEntity.ok(SalesHistoryResponseMessage.builder()
                .httpStatus(200)
                .msg("사원 통계(실적,수당,매출액) 월 별 검색 성공")
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
    @GetMapping("statistics")
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
    @GetMapping("statistics/search")
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
    @GetMapping("statistics/average/employee")
    public ResponseEntity<SalesHistoryResponseMessage> getStatisticsEmployeeAverageBySearch(@RequestBody SalesHistoryRankedDataDTO salesHistoryRankedDataDTO,
                                                                             @PageableDefault(size = 20) Pageable pageable){

        SalesHistoryStatisticsAverageDTO responseSalesHistory = salesHistoryQueryService.selectStatisticsAverageBySearch(salesHistoryRankedDataDTO,pageable);

        return ResponseEntity.ok(SalesHistoryResponseMessage.builder()
                .httpStatus(200)
                .msg("사원 별 통계(실적,수당,매출액) 조회기간별 평균 조회 성공")
                .result(responseSalesHistory)
                .build());
    }

    @Operation(summary = "매장 별 통계(실적,수당,매출액) 조회기간별 평균 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SalesHistoryResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("statistics/average/center")
    public ResponseEntity<SalesHistoryResponseMessage> getStatisticsCenterAverageBySearch(@RequestBody SalesHistoryRankedDataDTO salesHistoryRankedDataDTO,
                                                                                    @PageableDefault(size = 20) Pageable pageable){

        SalesHistoryStatisticsAverageDTO responseSalesHistory = salesHistoryQueryService.selectStatisticsAverageBySearch(salesHistoryRankedDataDTO,pageable);

        return ResponseEntity.ok(SalesHistoryResponseMessage.builder()
                .httpStatus(200)
                .msg("매장 별 통계(실적,수당,매출액) 조회기간별 평균 조회 성공")
                .result(responseSalesHistory)
                .build());
    }

    @Operation(summary = "사원 별 통계(실적,수당,매출액) 월별 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SalesHistoryResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("statistics/search/month")
    public ResponseEntity<SalesHistoryResponseMessage> getStatisticsBySearchMonth(@RequestBody SalesHistoryRankedDataDTO salesHistoryRankedDataDTO,
                                                                             @PageableDefault(size = 20) Pageable pageable){

        Page<SalesHistoryRankedDataDTO> responseSalesHistory = salesHistoryQueryService.selectStatisticsBySearchMonth(salesHistoryRankedDataDTO,pageable);

        return ResponseEntity.ok(SalesHistoryResponseMessage.builder()
                .httpStatus(200)
                .msg("사원 별 통계(실적,수당,매출액) 월별 검색 성공")
                .result(responseSalesHistory)
                .build());
    }

    @Operation(summary = "사원 별 통계(실적,수당,매출액) 연도별 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SalesHistoryResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("statistics/search/year")
    public ResponseEntity<SalesHistoryResponseMessage> getStatisticsBySearchYear(@RequestBody SalesHistoryRankedDataDTO salesHistoryRankedDataDTO,
                                                                             @PageableDefault(size = 20) Pageable pageable){

        Page<SalesHistoryRankedDataDTO> responseSalesHistory = salesHistoryQueryService.selectStatisticsBySearchYear(salesHistoryRankedDataDTO,pageable);

        return ResponseEntity.ok(SalesHistoryResponseMessage.builder()
                .httpStatus(200)
                .msg("사원 별 통계(실적,수당,매출액) 연도별 검색 성공")
                .result(responseSalesHistory)
                .build());
    }

    @Operation(summary = "매장 별 통계(실적,수당,매출액) 조회기간별 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SalesHistoryResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("statistics/center/search")
    public ResponseEntity<SalesHistoryResponseMessage> getStatisticsCenterBySearch(@RequestBody SalesHistoryRankedDataDTO salesHistoryRankedDataDTO,
                                                              @PageableDefault(size = 20) Pageable pageable){

        Page<SalesHistoryRankedDataDTO> responseSalesHistory = salesHistoryQueryService.selectStatisticsCenterBySearch(salesHistoryRankedDataDTO,pageable);

        return ResponseEntity.ok(SalesHistoryResponseMessage.builder()
                .httpStatus(200)
                .msg("매장 별 통계(실적,수당,매출액) 검색 성공")
                .result(responseSalesHistory)
                .build());
    }

@Operation(summary = "매장 별 통계(실적,수당,매출액) 월별 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SalesHistoryResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("statistics/center/search/month")
    public ResponseEntity<SalesHistoryResponseMessage> getStatisticsCenterBySearchMonth(@RequestBody SalesHistoryRankedDataDTO salesHistoryRankedDataDTO,
                                                              @PageableDefault(size = 20) Pageable pageable){

        Page<SalesHistoryRankedDataDTO> responseSalesHistory = salesHistoryQueryService.selectStatisticsCenterBySearchMonth(salesHistoryRankedDataDTO,pageable);

        return ResponseEntity.ok(SalesHistoryResponseMessage.builder()
                .httpStatus(200)
                .msg("매장 별 통계(실적,수당,매출액) 월별 검색 성공")
                .result(responseSalesHistory)
                .build());
    }

@Operation(summary = "매장 별 통계(실적,수당,매출액) 연도별 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SalesHistoryResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("statistics/center/search/year")
    public ResponseEntity<SalesHistoryResponseMessage> getStatisticsCenterBySearchYear(@RequestBody SalesHistoryRankedDataDTO salesHistoryRankedDataDTO,
                                                              @PageableDefault(size = 20) Pageable pageable){

        Page<SalesHistoryRankedDataDTO> responseSalesHistory = salesHistoryQueryService.selectStatisticsCenterBySearchYear(salesHistoryRankedDataDTO,pageable);

        return ResponseEntity.ok(SalesHistoryResponseMessage.builder()
                .httpStatus(200)
                .msg("매장 별 통계(실적,수당,매출액) 연도별 검색 성공")
                .result(responseSalesHistory)
                .build());
    }


}
