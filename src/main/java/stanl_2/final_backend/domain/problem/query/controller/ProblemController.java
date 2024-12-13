package stanl_2.final_backend.domain.problem.query.controller;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.problem.common.response.ProblemResponseMessage;
import stanl_2.final_backend.domain.problem.query.dto.ProblemDTO;
import stanl_2.final_backend.domain.problem.query.dto.ProblemSearchDTO;
import stanl_2.final_backend.domain.problem.query.service.ProblemService;

@RestController("queryProblemController")
@RequestMapping("/api/v1/problem")
public class ProblemController {

    private final ProblemService problemService;

    @Autowired
    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @Operation(summary = "문제사항 조건별 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = ProblemResponseMessage.class))})
    })
    @GetMapping
    public ResponseEntity<Page<ProblemDTO>> getProblems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String memberId,
            @RequestParam(required = false) String productId,
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    )
    {
        Pageable pageable = PageRequest.of(page, size);
        ProblemSearchDTO problemsearchDTO = new ProblemSearchDTO(title, memberId, productId, customerId, startDate, endDate);
        Page<ProblemDTO> problemDTOPage = problemService.findProblems(pageable, problemsearchDTO);

        return ResponseEntity.ok(problemDTOPage);
    }

    @Operation(summary = "문제사항 Id로 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = ProblemResponseMessage.class))})
    })
    @GetMapping("{problemId}")
    public ResponseEntity<ProblemDTO> getProblem(@PathVariable String problemId){
        ProblemDTO problemDTO = problemService.findProblem(problemId);
        return ResponseEntity.ok(problemDTO);
    }
    @Operation(summary = "문제사항 엑셀 다운 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "문제사항 엑셀 다운 테스트 성공",
                    content = {@Content(schema = @Schema(implementation = ProblemResponseMessage.class))}),
    })
    @GetMapping("/excel")
    public void exportNotice(HttpServletResponse response){

        problemService.exportProblemsToExcel(response);
    }
}
