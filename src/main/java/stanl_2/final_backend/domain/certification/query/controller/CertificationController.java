package stanl_2.final_backend.domain.certification.query.controller;

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
import stanl_2.final_backend.domain.career.common.response.CareerResponseMessage;
import stanl_2.final_backend.domain.certification.query.dto.CertificationDTO;
import stanl_2.final_backend.domain.certification.query.service.CertificationQueryService;

import java.security.Principal;
import java.util.List;

@RestController(value = "queryCertificationController")
@RequestMapping("/api/v1/certification")
public class CertificationController {

    private final CertificationQueryService certificationQueryService;

    @Autowired
    public CertificationController(CertificationQueryService certificationQueryService) {
        this.certificationQueryService = certificationQueryService;
    }

    @Operation(summary = "자격증/외국어 조회(with 사번)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = CareerResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/other/{loginId}")
    public ResponseEntity<CareerResponseMessage> getCertificationByOther(@PathVariable String loginId){

        List<CertificationDTO> certificationList = certificationQueryService.selectCertificationList(loginId);

        return ResponseEntity.ok(CareerResponseMessage.builder()
                                                       .httpStatus(200)
                                                       .msg("성공")
                                                       .result(certificationList)
                                                       .build());
    }

    @Operation(summary = "자격증/외국어 조회(접속중인 사용자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = CareerResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("")
    public ResponseEntity<CareerResponseMessage> getCertification(Principal principal){

        List<CertificationDTO> careerList = certificationQueryService.selectCertificationList(principal.getName());

        return ResponseEntity.ok(CareerResponseMessage.builder()
                                                       .httpStatus(200)
                                                       .msg("성공")
                                                       .result(careerList)
                                                       .build());
    }
}
