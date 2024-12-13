package stanl_2.final_backend.domain.organization.query.controller;

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
import stanl_2.final_backend.domain.organization.common.response.OrganizationResponseMessage;
import stanl_2.final_backend.domain.organization.query.dto.OrganizationDTO;
import stanl_2.final_backend.domain.organization.query.service.OrganizationQueryService;

import java.util.List;

@RestController(value = "queryOrganizationController")
@RequestMapping("/api/v1/organization")
public class OrganizationController {

    private final OrganizationQueryService organizationQueryService;

    @Autowired
    public OrganizationController(OrganizationQueryService organizationQueryService) {
        this.organizationQueryService = organizationQueryService;
    }


    @Operation(summary = "사이드바 메뉴 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = OrganizationResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("")
    public ResponseEntity<OrganizationResponseMessage> getOrganizationById() {

        List<OrganizationDTO> organizationDTO = organizationQueryService.selectOrganizationChart();

        return ResponseEntity.ok(OrganizationResponseMessage.builder()
                                                            .httpStatus(200)
                                                            .msg("성공")
                                                            .result(organizationDTO)
                                                            .build());
    }

}
