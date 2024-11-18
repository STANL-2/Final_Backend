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
import stanl_2.final_backend.domain.A_sample.common.response.SampleResponseMessage;
import stanl_2.final_backend.domain.career.common.response.CareerResponseMessage;
import stanl_2.final_backend.domain.career.query.dto.CareerDTO;
import stanl_2.final_backend.domain.career.query.service.CareerQueryService;

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

    @Operation(summary = "사번으로 경력 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SampleResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/other/{id}")
    public ResponseEntity<CareerResponseMessage> getCareer(@PathVariable String id){

        List<CareerDTO> careerList = careerQueryService.selectCareerList(id);

        return ResponseEntity.ok(CareerResponseMessage.builder()
                                                        .httpStatus(200)
                                                        .msg("성공")
                                                        .result(careerList)
                                                        .build());
    }
}
