package stanl_2.final_backend.domain.A_sample.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.A_sample.common.response.SampleResponseMessage;
import stanl_2.final_backend.domain.A_sample.query.dto.SampleDTO;
import stanl_2.final_backend.domain.A_sample.query.service.SampleQueryService;

import java.security.Principal;

@Slf4j
@RestController(value = "querySampleController")
@RequestMapping("/api/v1/sample")
public class SampleController {

    private final SampleQueryService sampleQueryService;

    @Autowired
    public SampleController(SampleQueryService sampleQueryService) {
        this.sampleQueryService = sampleQueryService;
    }

    /**
     * [GET] http://localhost:7777/api/v1/sample/SAM_000000001
     * */
    @Operation(summary = "샘플 조회 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SampleResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("{id}")
    public ResponseEntity<SampleResponseMessage> getTest(@PathVariable String id,
                                                         Principal principal){

        log.info("현재 접속한 회원정보(MEM_LOGIN_ID)");
        log.info(principal.getName());

        SampleDTO sampleDTO = sampleQueryService.selectSampleInfo(id);

        return ResponseEntity.ok(SampleResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(sampleDTO)
                .build());
    }

    /**
     * [GET] http://localhost:7777/api/v1/sample/detail/SAM_000000001
     * */
    @Operation(summary = "샘플 상세 조회 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SampleResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/detail/{id}")
    public ResponseEntity<SampleResponseMessage> getDetailTest(@PathVariable String id) {

        String name = sampleQueryService.selectSampleName(id);

        return ResponseEntity.ok(SampleResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(name)
                .build());
    }

    @Operation(summary = "샘플 엑셀 다운 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "샘플 엑설 다운 테스트 성공",
                    content = {@Content(schema = @Schema(implementation = SampleResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/excel")
    public void exportSample(HttpServletResponse response){

        sampleQueryService.exportSamplesToExcel(response);
    }
}
