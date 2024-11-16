package stanl_2.final_backend.domain.evaluation.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.A_sample.command.application.dto.SampleModifyDTO;
import stanl_2.final_backend.domain.A_sample.command.application.dto.SampleRegistDTO;
import stanl_2.final_backend.domain.A_sample.common.response.SampleResponseMessage;
import stanl_2.final_backend.domain.evaluation.command.application.dto.EvaluationModifyDTO;
import stanl_2.final_backend.domain.evaluation.command.application.dto.EvaluationRegistDTO;
import stanl_2.final_backend.domain.evaluation.command.application.service.EvaluationCommandService;
import stanl_2.final_backend.domain.evaluation.common.response.EvaluationResponseMessage;

@RestController("commandEvaluationController")
@RequestMapping("/api/v1/evaluation")
public class EvaluationController {

    private final EvaluationCommandService evaluationCommandService;

    @Autowired
    public EvaluationController(EvaluationCommandService evaluationCommandService) {
        this.evaluationCommandService = evaluationCommandService;
    }

    @Operation(summary = "평가서 요청 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = EvaluationResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<EvaluationResponseMessage> postTest(@RequestBody EvaluationRegistDTO evaluationRegistRequestDTO) {

        evaluationCommandService.registerEvaluation(evaluationRegistRequestDTO);

        return ResponseEntity.ok(EvaluationResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(null)
                .build());
    }

    /**
     * [PUT] http://localhost:7777/api/v1/sample?mem_id=SAM_000001
     * Request
     *  {
     *     "name": "abcc"
     *  }
     * */
    @Operation(summary = "평가서 수정 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SampleResponseMessage.class))})
    })
    @PutMapping("{id}")
    public ResponseEntity<EvaluationResponseMessage> putTest(@PathVariable String id,
                                                         @RequestBody EvaluationModifyDTO evaluationModifyRequestDTO) {

//        evaluationModifyRequestDTO.setEvaluationId(id);
        evaluationCommandService.modifyEvaluation(id,evaluationModifyRequestDTO);

        return ResponseEntity.ok(EvaluationResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(null)
                .build());
    }

    /**
     * [DELETE] http://localhost:7777/api/v1/sample?mem_id=SAM_000001
     * */
    @Operation(summary = "평가서 삭제 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = EvaluationResponseMessage.class))})
    })
    @DeleteMapping("{id}")
    public ResponseEntity<EvaluationResponseMessage> deleteTest(@PathVariable String id) {

        evaluationCommandService.deleteEvaluation(id);

        return ResponseEntity.ok(EvaluationResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(null)
                .build());
    }

}
