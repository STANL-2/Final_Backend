package stanl_2.final_backend.domain.education.query.controller;

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
import stanl_2.final_backend.domain.A_sample.common.response.SampleResponseMessage;
import stanl_2.final_backend.domain.career.common.response.CareerResponseMessage;
import stanl_2.final_backend.domain.certification.query.dto.CertificationDTO;
import stanl_2.final_backend.domain.education.common.response.EducationResponseMessage;
import stanl_2.final_backend.domain.education.query.dto.EducationDTO;
import stanl_2.final_backend.domain.education.query.service.EducationQueryService;

import java.security.Principal;
import java.util.List;

@RestController(value = "queryEducationController")
@RequestMapping("/api/v1/education")
public class EducationController {

    private final EducationQueryService educationQueryService;

    @Autowired
    public EducationController(EducationQueryService educationQueryService) {
        this.educationQueryService = educationQueryService;
    }

    @Operation(summary = "학력 조회(with 사번)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SampleResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/other/{loginId}")
    public ResponseEntity<EducationResponseMessage> getEducation(@PathVariable String loginId){

        List<EducationDTO> educationList = educationQueryService.selectEducationList(loginId);

        return ResponseEntity.ok(EducationResponseMessage.builder()
                                                         .httpStatus(200)
                                                         .msg("성공")
                                                         .result(educationList)
                                                         .build());
    }

    @Operation(summary = "학력 조회(접속중인 사용자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SampleResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("")
    public ResponseEntity<EducationResponseMessage> getCertification(Principal principal){

        List<EducationDTO> educationList = educationQueryService.selectEducationList(principal.getName());

        return ResponseEntity.ok(EducationResponseMessage.builder()
                                                         .httpStatus(200)
                                                         .msg("성공")
                                                         .result(educationList)
                                                         .build());
    }
}
