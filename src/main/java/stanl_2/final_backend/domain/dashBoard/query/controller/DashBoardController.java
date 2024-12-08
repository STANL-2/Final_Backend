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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.customer.common.response.CustomerResponseMessage;
import stanl_2.final_backend.domain.dashBoard.common.response.DashBoardResponseMessage;
import stanl_2.final_backend.domain.dashBoard.query.dto.DashBoardAdminDTO;
import stanl_2.final_backend.domain.dashBoard.query.dto.DashBoardDirectorDTO;
import stanl_2.final_backend.domain.dashBoard.query.dto.DashBoardEmployeeDTO;
import stanl_2.final_backend.domain.dashBoard.query.service.DashBoardQueryService;

import java.security.GeneralSecurityException;
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

    @Operation(summary = "대시보드 정보 조회 (사원)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "대시보드 조회 성공(사원)",
                    content = {@Content(schema = @Schema(implementation = CustomerResponseMessage.class))})
    })
    @GetMapping("/employee")
    public ResponseEntity<DashBoardResponseMessage> selectDashBoardForEmployee(Principal principal) throws GeneralSecurityException {

        String memberLoginId = principal.getName();

        DashBoardEmployeeDTO boardResponseDTO = dashBoardQueryService.selectInfoForEmployee(memberLoginId);

        return ResponseEntity.ok(DashBoardResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(boardResponseDTO)
                .build());
    }

    @Operation(summary = "대시보드 정보 조회 (관리자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "대시보드 조회 성공(관리자)",
                    content = {@Content(schema = @Schema(implementation = CustomerResponseMessage.class))})
    })
    @GetMapping("/admin")
    public ResponseEntity<DashBoardResponseMessage> selectDashBoardForAdmin(Principal principal) throws GeneralSecurityException {

        String memberLoginId = principal.getName();

        DashBoardAdminDTO boardResponseDTO = dashBoardQueryService.selectInfoForAdmin(memberLoginId);

        return ResponseEntity.ok(DashBoardResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(boardResponseDTO)
                .build());
    }

    @Operation(summary = "대시보드 정보 조회 (담당자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "대시보드 조회 성공(담당자)",
                    content = {@Content(schema = @Schema(implementation = CustomerResponseMessage.class))})
    })
    @GetMapping("/director")
    public ResponseEntity<DashBoardResponseMessage> selectDashBoardForDirector(Principal principal) throws GeneralSecurityException {

        String memberLoginId = principal.getName();

        DashBoardDirectorDTO boardResponseDTO = dashBoardQueryService.selectInfoForDirector(memberLoginId);

        return ResponseEntity.ok(DashBoardResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(boardResponseDTO)
                .build());
    }

}
