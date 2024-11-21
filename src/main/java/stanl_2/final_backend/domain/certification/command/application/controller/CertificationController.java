package stanl_2.final_backend.domain.certification.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.career.common.response.CareerResponseMessage;
import stanl_2.final_backend.domain.certification.command.application.dto.CertificationRegisterDTO;
import stanl_2.final_backend.domain.certification.command.application.service.CertificationCommandService;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;

import java.security.Principal;

@RestController("commandCertificationController")
@RequestMapping("/api/v1/certification")
public class CertificationController {

    private final CertificationCommandService certificationCommandService;
    private final AuthQueryService authQueryService;

    @Autowired
    public CertificationController(CertificationCommandService certificationCommandService,
                                   AuthQueryService authQueryService) {
        this.certificationCommandService = certificationCommandService;
        this.authQueryService = authQueryService;
    }

    @Operation(summary = "자격증/외국어 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = CareerResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<CareerResponseMessage> postCertification(@RequestBody CertificationRegisterDTO certificationRegisterDTO,
                                                                   Principal principal){

        certificationRegisterDTO.setMemberId(authQueryService.selectMemberIdByLoginId(principal.getName()));

        certificationCommandService.registCertification(certificationRegisterDTO);

        return ResponseEntity.ok(CareerResponseMessage.builder()
                                                      .httpStatus(200)
                                                      .msg("성공")
                                                      .result(null)
                                                      .build());
    }
}
