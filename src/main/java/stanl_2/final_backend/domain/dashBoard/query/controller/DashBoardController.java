package stanl_2.final_backend.domain.dashBoard.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.customer.common.response.CustomerResponseMessage;
import stanl_2.final_backend.domain.dashBoard.common.response.DashBoardResponseMessage;
import stanl_2.final_backend.domain.dashBoard.query.dto.DashBoardDTO;
import stanl_2.final_backend.domain.dashBoard.query.service.DashBoardQueryService;

import java.security.Principal;

@Slf4j
@RestController("queryDashBoardController")
@RequestMapping("/api/v1/dashBoard")
public class DashBoardController {

    private final DashBoardQueryService dashBoardQueryService;

    @Autowired
    public DashBoardController(DashBoardQueryService dashBoardQueryService) {
        this.dashBoardQueryService = dashBoardQueryService;
    }

    @Operation(summary = "대시보드 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "대시보드 조회 성공",
                    content = {@Content(schema = @Schema(implementation = CustomerResponseMessage.class))})
    })
    @GetMapping("")
    public ResponseEntity<DashBoardResponseMessage> selectDashBoard(Principal principal){

        String memberLoginId = principal.getName();

        DashBoardDTO boardResponseDTO = dashBoardQueryService.selectAllInfo(memberLoginId);

        return ResponseEntity.ok(DashBoardResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(null)
                .build());
    }

}
