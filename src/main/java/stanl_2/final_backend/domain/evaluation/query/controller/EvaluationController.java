package stanl_2.final_backend.domain.evaluation.query.controller;

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
import stanl_2.final_backend.domain.A_sample.query.dto.SampleDTO;
import stanl_2.final_backend.domain.A_sample.query.service.SampleQueryService;
import stanl_2.final_backend.domain.evaluation.common.response.EvaluationResponseMessage;
import stanl_2.final_backend.domain.evaluation.query.dto.EvaluationDTO;
import stanl_2.final_backend.domain.evaluation.query.service.EvaluationQueryService;

@RestController(value = "queryEvaluationController")
@RequestMapping("/api/v1/evaluation")
public class EvaluationController {
    private final EvaluationQueryService evaluationQueryService;

    @Autowired
    public EvaluationController(EvaluationQueryService evaluationQueryService) {
        this.evaluationQueryService = evaluationQueryService;
    }

    /**
     * [GET] http://localhost:7777/api/v1/sample/SAM_000000001
     * */
    @Operation(summary = "평가서 조회 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = EvaluationResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("{id}")
    public ResponseEntity<EvaluationResponseMessage> getTest(@PathVariable String id){

        EvaluationDTO evaluationDTO = evaluationQueryService.selectEvaluation(id);

        return ResponseEntity.ok(EvaluationResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(evaluationDTO)
                .build());
    }

    /**
     * [GET] http://localhost:7777/api/v1/sample/detail/SAM_000000001
     * */
    @Operation(summary = "평가서 상세 조회 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = EvaluationResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/detail/{id}")
    public ResponseEntity<EvaluationResponseMessage> getDetailTest(@PathVariable String id) {

        String name = evaluationQueryService.selectEvaluationName(id);

        return ResponseEntity.ok(EvaluationResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(name)
                .build());
    }

}
