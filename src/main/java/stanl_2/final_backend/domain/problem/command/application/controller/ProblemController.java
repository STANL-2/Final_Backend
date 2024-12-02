package stanl_2.final_backend.domain.problem.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.problem.command.application.dto.ProblemModifyDTO;
import stanl_2.final_backend.domain.problem.command.application.dto.ProblemRegistDTO;
import stanl_2.final_backend.domain.problem.command.application.service.ProblemCommandService;
import stanl_2.final_backend.domain.problem.common.response.ProblemResponseMessage;
import stanl_2.final_backend.domain.s3.command.domain.service.S3FileServiceImpl;

import java.security.Principal;

@RestController("commandProblemController")
@RequestMapping("/api/v1/problem")
public class ProblemController {
    private final ProblemCommandService problemCommandService;
    private final AuthQueryService authQueryService;
    private final S3FileServiceImpl s3FileService;
    private final ProblemModifyDTO problemModifyDTO;
    @Autowired
    public ProblemController(ProblemCommandService problemCommandService, AuthQueryService authQueryService, S3FileServiceImpl s3FileService,ProblemModifyDTO problemModifyDTO ) {
        this.problemCommandService = problemCommandService;
        this.authQueryService = authQueryService;
        this.s3FileService = s3FileService;
        this.problemModifyDTO =problemModifyDTO;
    }

    @Operation(summary = "문제사항 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = ProblemResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<ProblemResponseMessage> postProblem(@RequestPart("dto") ProblemRegistDTO problemRegistDTO, // JSON 데이터
                                                              @RequestPart("file") MultipartFile file,
                                                              Principal principal){
        String memberId = authQueryService.selectMemberIdByLoginId(principal.getName());
        problemRegistDTO.setMemberId(memberId);
        if (file != null && !file.isEmpty()) {
            problemRegistDTO.setFileUrl(s3FileService.uploadOneFile(file));
            System.out.println("response:1");
        }else if(file==null || file.isEmpty()){
            System.out.println("response:2");
            problemRegistDTO.setFileUrl(null);
        } else {
            System.out.println("response:3");
            problemRegistDTO.setFileUrl(null);
        }
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
                                                              @RequestBody ProblemModifyDTO problemModifyRequestDTO,
                                                                @RequestPart(value = "file", required = false)  MultipartFile file,
                                                                Principal principal){
        System.out.println("==============================");
        String memberLoginId = principal.getName();
        System.out.println("==============================");
        problemModifyRequestDTO.setMemberId(memberLoginId);
        System.out.println("==============================");
        problemModifyRequestDTO.setContent(problemModifyRequestDTO.getContent());
        if (file != null && !file.isEmpty()) {
            System.out.println("response:1");
            problemModifyRequestDTO.setFileUrl(s3FileService.uploadOneFile(file));
        }else if(file==null || file.isEmpty()) {
            System.out.println("response:2");
            problemModifyRequestDTO.setFileUrl(null);
        } else {
            System.out.println("response:3");
            problemModifyRequestDTO.setFileUrl(s3FileService.uploadOneFile(file));
        }
         problemCommandService.modifyProblem(problemId,problemModifyRequestDTO,principal);

        return ResponseEntity.ok(ProblemResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(problemModifyDTO)
                .build());
    }

    @Operation(summary = "문제사항 상태 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = ProblemResponseMessage.class))})
    })
    @PutMapping("/status/{problemId}")
    public ResponseEntity<ProblemResponseMessage> modifyProblemStatus(@PathVariable String problemId, Principal principal){

        problemCommandService.modifyStatus(problemId,principal);

        return ResponseEntity.ok(ProblemResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(null)
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
