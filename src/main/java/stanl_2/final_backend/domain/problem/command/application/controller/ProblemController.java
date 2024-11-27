package stanl_2.final_backend.domain.problem.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.problem.command.application.dto.ProblemModifyDTO;
import stanl_2.final_backend.domain.problem.command.application.dto.ProblemRegistDTO;
import stanl_2.final_backend.domain.problem.command.application.service.ProblemCommandService;
import stanl_2.final_backend.domain.problem.common.response.ProblemResponseMessage;

import java.security.Principal;

@RestController("commandProblemController")
@RequestMapping("/api/v1/problem")
public class ProblemController {
    private final ProblemCommandService problemCommandService;
    private final AuthQueryService authQueryService;
    @Autowired
    public ProblemController(ProblemCommandService problemCommandService, AuthQueryService authQueryService) {
        this.problemCommandService = problemCommandService;
        this.authQueryService = authQueryService;
    }

    @Operation(summary = "문제사항 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = ProblemResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<ProblemResponseMessage> postProblem(@RequestBody ProblemRegistDTO problemRegistDTO, Principal principal){
        String memberId = authQueryService.selectMemberIdByLoginId(principal.getName());
        problemRegistDTO.setMemberId(memberId);
        problemCommandService.registerProblem(problemRegistDTO,principal);
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
                                                              @RequestBody ProblemModifyDTO problemModifyRequestDTO, Principal principal){

        ProblemModifyDTO problemModifyDTO = problemCommandService.modifyProblem(problemId,problemModifyRequestDTO,principal);

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
    public ResponseEntity<ProblemResponseMessage> deleteProblem(@PathVariable String problemId, Principal principal) {

        problemCommandService.deleteProblem(problemId,principal);

        return ResponseEntity.ok(ProblemResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(null)
                .build());
    }
}
