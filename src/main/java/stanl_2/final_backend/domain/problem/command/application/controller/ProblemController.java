package stanl_2.final_backend.domain.problem.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.problem.command.application.dto.ProblemModifyDTO;
import stanl_2.final_backend.domain.problem.command.application.dto.ProblemRegistDTO;
import stanl_2.final_backend.domain.problem.command.application.service.ProblemCommandService;
import stanl_2.final_backend.domain.problem.common.response.ProblemResponseMessage;

@RestController("commandProblemController")
@RequestMapping("/api/v1/problem")
public class ProblemController {
    private final ProblemCommandService problemCommandService;

    @Autowired
    public ProblemController(ProblemCommandService problemCommandService) {
        this.problemCommandService = problemCommandService;
    }

    @Operation(summary = "문제사항 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = ProblemResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<ProblemResponseMessage> postNotice(@RequestBody ProblemRegistDTO problemRegistDTO){
        problemCommandService.registerProblem(problemRegistDTO);
        return ResponseEntity.ok(ProblemResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(null)
                .build());

    }
    @Operation(summary = "문제사항 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = ProblemResponseMessage.class))})
    })
    @PutMapping("{problemId}")
    public ResponseEntity<ProblemResponseMessage> modifyProblem(@PathVariable String problemId,
                                                              @RequestBody ProblemModifyDTO problemModifyRequestDTO){

        ProblemModifyDTO problemModifyDTO = problemCommandService.modifyProblem(problemId,problemModifyRequestDTO);

        return ResponseEntity.ok(ProblemResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(problemModifyDTO)
                .build());
    }

    @Operation(summary = "문제사항 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = ProblemResponseMessage.class))})
    })
    @DeleteMapping("{problemId}")
    public ResponseEntity<ProblemResponseMessage> deleteProblem(@PathVariable String problemId) {

        problemCommandService.deleteProblem(problemId);

        return ResponseEntity.ok(ProblemResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(null)
                .build());
    }
}
