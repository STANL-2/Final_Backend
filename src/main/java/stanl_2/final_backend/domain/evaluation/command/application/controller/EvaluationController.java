package stanl_2.final_backend.domain.evaluation.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    @Operation(summary = "평가서 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = EvaluationResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<EvaluationResponseMessage> postEvaluation(@RequestPart("dto") EvaluationRegistDTO evaluationRegistRequestDTO,
                                                                    @RequestPart("file") MultipartFile fileUrl) {

        evaluationCommandService.registerEvaluation(evaluationRegistRequestDTO, fileUrl);

        return ResponseEntity.ok(EvaluationResponseMessage.builder()
                .httpStatus(200)
                .msg("평가서 등록 성공")
                .result(null)
                .build());
    }

    @Operation(summary = "평가서 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SampleResponseMessage.class))})
    })
    @PutMapping("{id}")
    public ResponseEntity<EvaluationResponseMessage> putEvaluation(@PathVariable String id,
                                                         @RequestPart("dto") EvaluationModifyDTO evaluationModifyRequestDTO,
                                                                   @RequestPart("file") MultipartFile fileUrl) {

        evaluationModifyRequestDTO.setEvaluationId(id);

        evaluationCommandService.modifyEvaluation(evaluationModifyRequestDTO, fileUrl);

        return ResponseEntity.ok(EvaluationResponseMessage.builder()
                .httpStatus(200)
                .msg("평가서 수정 성공")
                .result(null)
                .build());
    }

    @Operation(summary = "평가서 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = EvaluationResponseMessage.class))})
    })
    @DeleteMapping("{id}")
    public ResponseEntity<EvaluationResponseMessage> deleteEvaluation(@PathVariable String id) {

        evaluationCommandService.deleteEvaluation(id);

        return ResponseEntity.ok(EvaluationResponseMessage.builder()
                .httpStatus(200)
                .msg("평가서 삭제 성공")
                .result(null)
                .build());
    }

}
