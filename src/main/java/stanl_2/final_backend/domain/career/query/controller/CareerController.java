package stanl_2.final_backend.domain.career.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.career.common.response.CareerResponseMessage;
import stanl_2.final_backend.domain.career.query.dto.CareerDTO;
import stanl_2.final_backend.domain.career.query.service.CareerQueryService;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController(value = "queryCareerController")
@RequestMapping("/api/v1/career")
public class CareerController {

    private final CareerQueryService careerQueryService;

    @Autowired
    public CareerController(CareerQueryService careerQueryService) {
        this.careerQueryService = careerQueryService;
    }

    @Operation(summary = "경력 조회(with 사번)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = CareerResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/other/{loginId}")
    public ResponseEntity<CareerResponseMessage> getCareerByOther(@PathVariable String loginId){

        List<CareerDTO> careerList = careerQueryService.selectCareerList(loginId);

        return ResponseEntity.ok(CareerResponseMessage.builder()
                                                       .httpStatus(200)
                                                       .msg("성공")
                                                       .result(careerList)
                                                       .build());
    }

    @Operation(summary = "경력 조회(접속중인 사용자)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = CareerResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("")
    public ResponseEntity<CareerResponseMessage> getCareer(Principal principal){

        List<CareerDTO> careerList = careerQueryService.selectCareerList(principal.getName());

        return ResponseEntity.ok(CareerResponseMessage.builder()
                                                       .httpStatus(200)
                                                       .msg("성공")
                                                       .result(careerList)
                                                       .build());
    }
}
