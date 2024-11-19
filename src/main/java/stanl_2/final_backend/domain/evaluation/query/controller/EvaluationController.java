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
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.evaluation.common.response.EvaluationResponseMessage;
import stanl_2.final_backend.domain.evaluation.query.dto.EvaluationDTO;
import stanl_2.final_backend.domain.evaluation.query.dto.EvaluationSearchDTO;
import stanl_2.final_backend.domain.evaluation.query.service.EvaluationQueryService;
import stanl_2.final_backend.domain.product.common.response.ProductResponseMessage;
import stanl_2.final_backend.domain.product.query.dto.ProductSearchRequestDTO;

import java.security.GeneralSecurityException;
import java.util.HashMap;
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
    @Operation(summary = "평가서 전체 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = EvaluationResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("")
    public ResponseEntity<EvaluationResponseMessage> getAllEvaluations(Authentication authentication,
                                                                           Pageable pageable) throws GeneralSecurityException {
        EvaluationDTO evaluationDTO = new EvaluationDTO();

        evaluationDTO.setRoles(authentication.getAuthorities());
        evaluationDTO.setMemberId(authentication.getName());

        Page<EvaluationDTO> responseEvaluations = evaluationQueryService.selectAllEvaluations(evaluationDTO, pageable);

        return ResponseEntity.ok(EvaluationResponseMessage.builder()
                .httpStatus(200)
                .msg("평가서 전체 조회 성공")
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
                .msg("평가서 상세 조회 성공")
                .result(evaluationDTO)
                .build());
    }

    @Operation(summary = "평가서 검색 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = EvaluationResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/search")
    public ResponseEntity<EvaluationResponseMessage> getEvaluationBySearch(@RequestParam Map<String, String> params
                                                                           ,Authentication authentication
                                                                        , @PageableDefault(size = 20) Pageable pageable) throws GeneralSecurityException {

        EvaluationSearchDTO evaluationSearchDTO = new EvaluationSearchDTO();
        evaluationSearchDTO.setEvalId(params.get("evalId"));
        evaluationSearchDTO.setTitle(params.get("title"));
        evaluationSearchDTO.setWriterName(params.get("writerName"));
        evaluationSearchDTO.setMemberName(params.get("memberName"));
        evaluationSearchDTO.setCenterId(params.get("centerId"));
        evaluationSearchDTO.setStartDate(params.get("startDate"));
        evaluationSearchDTO.setEndDate(params.get("endDate"));
        evaluationSearchDTO.setRoles(authentication.getAuthorities());
        evaluationSearchDTO.setSearcherName(authentication.getName());

        Page<EvaluationDTO> responseEvaluations = evaluationQueryService.selectEvaluationBySearch(pageable, evaluationSearchDTO);

        return ResponseEntity.ok(EvaluationResponseMessage.builder()
                .httpStatus(200)
                .msg("검색 조회 성공")
                .result(responseEvaluations)
                .build());
    }

}
