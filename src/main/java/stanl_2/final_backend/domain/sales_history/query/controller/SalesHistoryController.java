package stanl_2.final_backend.domain.sales_history.query.controller;

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
import stanl_2.final_backend.domain.sales_history.common.response.SalesHistoryResponseMessage;
import stanl_2.final_backend.domain.sales_history.query.dto.SalesHistorySelectAllDTO;
import stanl_2.final_backend.domain.sales_history.query.service.SalesHistoryQueryService;

@RestController(value = "querySalesHistoryController")
@RequestMapping("/api/v1/salesHistory")
public class SalesHistoryController {
    private final SalesHistoryQueryService salesHistoryQueryService;

    @Autowired
    public SalesHistoryController(SalesHistoryQueryService salesHistoryQueryService) {
        this.salesHistoryQueryService = salesHistoryQueryService;
    }

    @Operation(summary = "판매내역 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SalesHistoryResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("")
    public ResponseEntity<SalesHistoryResponseMessage> getAllSalesHistory(){

        SalesHistorySelectAllDTO salesHistorySelectAllDTO = salesHistoryQueryService.selectAllSalesHistory();

        return ResponseEntity.ok(SalesHistoryResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(salesHistorySelectAllDTO)
                .build());
    }


}
