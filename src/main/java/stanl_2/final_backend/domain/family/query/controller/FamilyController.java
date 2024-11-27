package stanl_2.final_backend.domain.family.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.family.common.response.FamilyResponseMessage;
import stanl_2.final_backend.domain.family.query.dto.FamilyDTO;
import stanl_2.final_backend.domain.family.query.service.FamilyQueryService;

import java.security.Principal;
import java.util.List;

@RestController(value = "queryFamilyController")
@RequestMapping("/api/v1/family")
public class FamilyController {

    private final FamilyQueryService familyQueryService;

    @Autowired
    public FamilyController(FamilyQueryService familyQueryService) {
        this.familyQueryService = familyQueryService;
    }

    @Operation(summary = "가족 구성원 조회(with 사번)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = FamilyResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/other/{loginId}")
    public ResponseEntity<FamilyResponseMessage> getEducation(@PathVariable String loginId){

        List<FamilyDTO> familyList = familyQueryService.selectFamilyList(loginId);

        return ResponseEntity.ok(FamilyResponseMessage.builder()
                                                      .httpStatus(200)
                                                      .msg("성공")
                                                      .result(familyList)
                                                      .build());
    }

    @Operation(summary = "가족 구성원 조회(접속중인 사용자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = FamilyResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("")
    public ResponseEntity<FamilyResponseMessage> getCertification(Principal principal){

        List<FamilyDTO> familyList = familyQueryService.selectFamilyList(principal.getName());

        return ResponseEntity.ok(FamilyResponseMessage.builder()
                                                      .httpStatus(200)
                                                      .msg("성공")
                                                      .result(familyList)
                                                      .build());
    }
}
