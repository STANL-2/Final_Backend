package stanl_2.final_backend.domain.evaluation.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.evaluation.common.response.EvaluationResponseMessage;
import stanl_2.final_backend.domain.evaluation.query.dto.EvaluationDTO;
import stanl_2.final_backend.domain.evaluation.query.dto.EvaluationSearchDTO;
import stanl_2.final_backend.domain.evaluation.query.service.EvaluationQueryService;
import stanl_2.final_backend.domain.product.common.response.ProductResponseMessage;

import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.Map;

@RestController(value = "queryEvaluationController")
@RequestMapping("/api/v1/evaluation")
public class EvaluationController {

    private final EvaluationQueryService evaluationQueryService;

    @Autowired
    public EvaluationController(EvaluationQueryService evaluationQueryService) {
        this.evaluationQueryService = evaluationQueryService;
    }

    @Operation(summary = "평가서 관리자 전체 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = EvaluationResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/manager")
    public ResponseEntity<EvaluationResponseMessage> getAllEvaluationsByManager(Principal principal,
                                                                                @PageableDefault(size = 20) Pageable pageable,
                                                                                @RequestParam(required = false) String sortField,
                                                                                @RequestParam(required = false) String sortOrder) throws GeneralSecurityException {
        EvaluationDTO evaluationDTO = new EvaluationDTO();

        evaluationDTO.setMemberId(principal.getName());

        if (sortField != null && sortOrder != null) {
            Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, sortField));
        }

        Page<EvaluationDTO> responseEvaluations = evaluationQueryService.selectAllEvaluationsByManager(evaluationDTO, pageable);

        return ResponseEntity.ok(EvaluationResponseMessage.builder()
                .httpStatus(200)
                .msg("평가서 관리자 전체 조회 성공")
                .result(responseEvaluations)
                .build());
    }

    @Operation(summary = "평가서 담당자 전체 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = EvaluationResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/representative")
    public ResponseEntity<EvaluationResponseMessage> getAllEvaluationsByRepresentative(Principal principal,
                                                                                       @PageableDefault(size = 20) Pageable pageable,
                                                                                       @RequestParam(required = false) String sortField,
                                                                                       @RequestParam(required = false) String sortOrder) throws GeneralSecurityException {
        EvaluationDTO evaluationDTO = new EvaluationDTO();

        evaluationDTO.setMemberId(principal.getName());

        if (sortField != null && sortOrder != null) {
            Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, sortField));
        }

        Page<EvaluationDTO> responseEvaluations = evaluationQueryService.selectAllEvaluationsByRepresentative(evaluationDTO, pageable);

        return ResponseEntity.ok(EvaluationResponseMessage.builder()
                .httpStatus(200)
                .msg("평가서 담당자 전체 조회 성공")
                .result(responseEvaluations)
                .build());
    }

    @Operation(summary = "평가서 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = EvaluationResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("{id}")
    public ResponseEntity<EvaluationResponseMessage> getEvaluationDetail(@PathVariable String id) {

        EvaluationDTO evaluationDTO  = evaluationQueryService.selectEvaluationById(id);

        return ResponseEntity.ok(EvaluationResponseMessage.builder()
                .httpStatus(200)
                .msg("평가서 상세 조회 성공")
                .result(evaluationDTO)
                .build());
    }


    @Operation(summary = "평가서 관리자 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = EvaluationResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/manager/search")
    public ResponseEntity<EvaluationResponseMessage> getEvaluationBySearchByManager(@RequestParam Map<String, String> params
                                                                                    ,Principal principal
                                                                                    ,@PageableDefault(size = 20) Pageable pageable,
                                                                                    @RequestParam(required = false) String sortField,
                                                                                    @RequestParam(required = false) String sortOrder) throws GeneralSecurityException {

        EvaluationSearchDTO evaluationSearchDTO = new EvaluationSearchDTO();
        evaluationSearchDTO.setEvaluationId(params.get("evaluationId"));
        evaluationSearchDTO.setTitle(params.get("title"));
        evaluationSearchDTO.setWriterName(params.get("writerName"));
        evaluationSearchDTO.setMemberName(params.get("memberName"));
        evaluationSearchDTO.setCenterId(params.get("centerId"));
        evaluationSearchDTO.setStartDate(params.get("startDate"));
        evaluationSearchDTO.setEndDate(params.get("endDate"));
        evaluationSearchDTO.setSearcherName(principal.getName());

        if (sortField != null && sortOrder != null) {
            Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, sortField));
        }

        Page<EvaluationDTO> responseEvaluations = evaluationQueryService.selectEvaluationBySearchByManager(pageable, evaluationSearchDTO);

        return ResponseEntity.ok(EvaluationResponseMessage.builder()
                .httpStatus(200)
                .msg("관리자 검색 조회 성공")
                .result(responseEvaluations)
                .build());
    }

    @Operation(summary = "평가서 담당자 검색")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = EvaluationResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/representative/search")
    public ResponseEntity<EvaluationResponseMessage> getEvaluationBySearchByRepresentative(@RequestParam Map<String, String> params
                                                                                            ,Principal principal
                                                                                            ,@PageableDefault(size = 20) Pageable pageable,
                                                                                            @RequestParam(required = false) String sortField,
                                                                                            @RequestParam(required = false) String sortOrder) throws GeneralSecurityException {

        EvaluationSearchDTO evaluationSearchDTO = new EvaluationSearchDTO();
        evaluationSearchDTO.setEvaluationId(params.get("evaluationId"));
        evaluationSearchDTO.setTitle(params.get("title"));
        evaluationSearchDTO.setWriterName(params.get("writerName"));
        evaluationSearchDTO.setMemberName(params.get("memberName"));
        evaluationSearchDTO.setCenterId(params.get("centerId"));
        evaluationSearchDTO.setStartDate(params.get("startDate"));
        evaluationSearchDTO.setEndDate(params.get("endDate"));
        evaluationSearchDTO.setSearcherName(principal.getName());

        if (sortField != null && sortOrder != null) {
            Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(direction, sortField));
        }

        Page<EvaluationDTO> responseEvaluations = evaluationQueryService.selectEvaluationBySearchByRepresentative(pageable, evaluationSearchDTO);

        return ResponseEntity.ok(EvaluationResponseMessage.builder()
                .httpStatus(200)
                .msg("담당자 검색 조회 성공")
                .result(responseEvaluations)
                .build());
    }

    @Operation(summary = "평가서 엑셀 다운")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "평가서 엑셀 다운 성공",
                    content = {@Content(schema = @Schema(implementation = ProductResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/excel")
    public void exportEvaluation(HttpServletResponse response){

        evaluationQueryService.exportEvaluationToExcel(response);
    }

}
