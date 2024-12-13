package stanl_2.final_backend.domain.career.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.career.command.application.dto.CareerRegistDTO;
import stanl_2.final_backend.domain.career.command.application.service.CareerCommandService;
import stanl_2.final_backend.domain.career.common.response.CareerResponseMessage;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;

import java.security.Principal;

@Slf4j
@RestController("commandCareerController")
@RequestMapping("/api/v1/career")
public class CareerController {

    private final CareerCommandService careerCommandService;
    private final AuthQueryService authQueryService;

    @Autowired
    public CareerController(CareerCommandService careerCommandService,
                            AuthQueryService authQueryService) {
        this.careerCommandService = careerCommandService;
        this.authQueryService = authQueryService;
    }

    @Operation(summary = "경력 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = CareerResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<CareerResponseMessage> postCareer(@RequestBody CareerRegistDTO careerRegistDTO,
                                                            Principal principal){

        careerRegistDTO.setMemberId(authQueryService.selectMemberIdByLoginId(principal.getName()));

        careerCommandService.registCareer(careerRegistDTO);

        return ResponseEntity.ok(CareerResponseMessage.builder()
                                                        .httpStatus(200)
                                                        .msg("성공")
                                                        .result(null)
                                                        .build());
    }
}
