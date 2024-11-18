package stanl_2.final_backend.domain.evaluation.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.evaluation.common.response.EvaluationResponseMessage;
import stanl_2.final_backend.domain.evaluation.query.dto.EvaluationDTO;
import stanl_2.final_backend.domain.evaluation.query.service.EvaluationQueryService;

import java.util.Map;

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
        @Operation(summary = "평가서 담당자 조회")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "성공",
                        content = {@Content(schema = @Schema(implementation = EvaluationResponseMessage.class))}),
                @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                        content = @Content(mediaType = "application/json"))
        })
        @GetMapping("")
        public ResponseEntity<EvaluationResponseMessage> getAllEvaluations(@PageableDefault(size = 20) Pageable pageable,
                                                                           Authentication authentication){

            EvaluationDTO evaluationDTO = new EvaluationDTO();

            evaluationDTO.setRoles(authentication.getAuthorities());

            Page<Map<String, Object>> responseEvaluations = evaluationQueryService.selectAllEvaluations(pageable, evaluationDTO);

            return ResponseEntity.ok(EvaluationResponseMessage.builder()
                    .httpStatus(200)
                    .msg("성공")
                    .result(responseEvaluations)
                    .build());
    }

    /**
     * [GET] http://localhost:7777/api/v1/sample/SAM_000000001
     * */
    @Operation(summary = "평가서 관리자 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = EvaluationResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{centerId}")
    public ResponseEntity<EvaluationResponseMessage> getEvaluationByCenter(@PathVariable String centerId, Pageable pageable){

        Page<EvaluationDTO> responseEvaluations = evaluationQueryService.selectEvaluationByCenter(centerId, pageable);

        return ResponseEntity.ok(EvaluationResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(responseEvaluations)
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
    public ResponseEntity<EvaluationResponseMessage> getEvaluationDetail(@PathVariable String id) {

        EvaluationDTO evaluationDTO  = evaluationQueryService.selectEvaluationById(id);

        return ResponseEntity.ok(EvaluationResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(evaluationDTO)
                .build());
    }

}
