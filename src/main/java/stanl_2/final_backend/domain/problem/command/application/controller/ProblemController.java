package stanl_2.final_backend.domain.problem.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.domain.problem.command.application.dto.ProblemModifyDTO;
import stanl_2.final_backend.domain.problem.command.application.dto.ProblemRegistDTO;
import stanl_2.final_backend.domain.problem.command.application.service.ProblemCommandService;
import stanl_2.final_backend.domain.problem.command.domain.aggregate.entity.Problem;
import stanl_2.final_backend.domain.problem.common.response.ProblemResponseMessage;
import stanl_2.final_backend.domain.s3.command.domain.service.S3FileServiceImpl;

import java.security.GeneralSecurityException;
import java.security.Principal;

@RestController("commandProblemController")
@RequestMapping("/api/v1/problem")
public class ProblemController {
    private final ProblemCommandService problemCommandService;
    private final AuthQueryService authQueryService;
    private final S3FileServiceImpl s3FileService;
    private final MemberQueryService memberQueryService;
    private final ModelMapper modelMapper;
    @Autowired
    public ProblemController(ProblemCommandService problemCommandService, AuthQueryService authQueryService, S3FileServiceImpl s3FileService,
                             MemberQueryService memberQueryService, ModelMapper modelMapper) {
        this.problemCommandService = problemCommandService;
        this.authQueryService = authQueryService;
        this.s3FileService = s3FileService;
        this.memberQueryService =memberQueryService;
        this.modelMapper =modelMapper;
    }

    @Operation(summary = "문제사항 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = ProblemResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<ProblemResponseMessage> postProblem(@RequestPart("dto") ProblemRegistDTO problemRegistDTO, // JSON 데이터
                                                              @RequestPart(value = "file", required = false)  MultipartFile file,
                                                              Principal principal) throws GeneralSecurityException {
        String memberLoginId = principal.getName();
        problemRegistDTO.setMemberLoginId(memberLoginId);
        if (file != null && !file.isEmpty()) {
            problemRegistDTO.setFileUrl(s3FileService.uploadOneFile(file));
        }else if(file==null || file.isEmpty()){
            problemRegistDTO.setFileUrl(null);
        } else {
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
                                                                @RequestPart("dto") ProblemModifyDTO problemModifyDTO,
                                                                @RequestPart(value = "file", required = false)  MultipartFile file,
                                                                Principal principal) throws GeneralSecurityException {
        String memberLoginId = principal.getName();
        String memberId = authQueryService.selectMemberIdByLoginId(memberLoginId);
        memberId=memberQueryService.selectNameById(memberId);
        problemModifyDTO.setMemberId(memberId);
        problemModifyDTO.setMemberLoginId(memberLoginId);
        problemModifyDTO.setContent(problemModifyDTO.getContent());
        problemModifyDTO.setFileUrl(problemModifyDTO.getFileUrl());
        Problem updateProblem = modelMapper.map(problemModifyDTO, Problem.class);
        System.out.println("1."+updateProblem.getFileUrl());
        if(problemModifyDTO.getFileUrl()==null){
            System.out.println("테스트중");
            problemModifyDTO.setFileUrl(updateProblem.getFileUrl());
        }
        if (file != null && !file.isEmpty()) {
            System.out.println("1번");
            problemModifyDTO.setFileUrl(s3FileService.uploadOneFile(file));
        }else if(file==null || file.isEmpty()) {
            System.out.println("2번");
            problemModifyDTO.setFileUrl(updateProblem.getFileUrl());
        } else {
            System.out.println("3번");
            problemModifyDTO.setFileUrl(s3FileService.uploadOneFile(file));
        }
        problemCommandService.modifyProblem(problemId,problemModifyDTO,principal);
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
