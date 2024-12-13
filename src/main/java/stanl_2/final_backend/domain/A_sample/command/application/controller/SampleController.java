package stanl_2.final_backend.domain.A_sample.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import stanl_2.final_backend.domain.A_sample.command.application.dto.SampleRegistDTO;
import stanl_2.final_backend.domain.A_sample.command.application.dto.SampleModifyDTO;
import stanl_2.final_backend.domain.A_sample.command.application.service.SampleCommandService;
import stanl_2.final_backend.domain.A_sample.common.response.SampleResponseMessage;

import java.security.Principal;

@Slf4j
@RestController("commandSampleController")
@RequestMapping("/api/v1/sample")
public class SampleController {

    private final SampleCommandService sampleCommandService;

    @Autowired
    public SampleController(SampleCommandService sampleCommandService) {
        this.sampleCommandService = sampleCommandService;
    }

    /**
     * [POST] http://localhost:7777/api/v1/sample
     * Request
     *  {
     *     "name": "tes1",
     *     "num": 123
     *  }
     * */
    @Operation(summary = "샘플 요청 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                        content = {@Content(schema = @Schema(implementation = SampleResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<SampleResponseMessage> postTest(@RequestBody SampleRegistDTO sampleRegistRequestDTO,
                                                          Principal principal) {


        log.info("현재 접속한 회원정보(MEM_LOGIN_ID)");
        log.info(principal.getName());

        sampleCommandService.registerSample(sampleRegistRequestDTO);

        return ResponseEntity.ok(SampleResponseMessage.builder()
                                                .httpStatus(200)
                                                .msg("성공")
                                                .result(null)
                                                .build());
    }

    @Operation(summary = "샘플 파일 요청 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SampleResponseMessage.class))})
    })
    @PostMapping("/file")
    public ResponseEntity<SampleResponseMessage> postTestFile(@RequestPart("dto") SampleRegistDTO sampleRegistRequestDTO,
                                                              Principal principal,
                                                              @RequestPart("file") MultipartFile imageUrl) {


        log.info("현재 접속한 회원정보(MEM_LOGIN_ID)");
        log.info(principal.getName());
        sampleCommandService.registerSampleFile(sampleRegistRequestDTO, imageUrl);

        return ResponseEntity.ok(SampleResponseMessage.builder()
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
    @Operation(summary = "샘플 수정 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SampleResponseMessage.class))})
    })
    @PutMapping("{id}")
    public ResponseEntity<SampleResponseMessage> putTest(@PathVariable String id,
                                                         @RequestBody SampleModifyDTO sampleModifyRequestDTO,
                                                         Principal principal) {

        log.info("현재 접속한 회원정보(MEM_LOGIN_ID)");
        log.info(principal.getName());

        sampleModifyRequestDTO.setId(id);
        SampleModifyDTO sampleModifyDTO = sampleCommandService.modifySample(id, sampleModifyRequestDTO);

        return ResponseEntity.ok(SampleResponseMessage.builder()
                                                .httpStatus(200)
                                                .msg("성공")
                                                .result(sampleModifyDTO)
                                                .build());
    }

    /**
     * [DELETE] http://localhost:7777/api/v1/sample?mem_id=SAM_000001
     * */
    @Operation(summary = "샘플 삭제 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SampleResponseMessage.class))})
    })
    @DeleteMapping("{id}")
    public ResponseEntity<SampleResponseMessage> deleteTest(@PathVariable String id,
                                                            Principal principal) {

        log.info("현재 접속한 회원정보(MEM_LOGIN_ID)");
        log.info(principal.getName());

        sampleCommandService.deleteSample(id);

        return ResponseEntity.ok(SampleResponseMessage.builder()
                                                .httpStatus(200)
                                                .msg("성공")
                                                .result(null)
                                                .build());
    }

}
