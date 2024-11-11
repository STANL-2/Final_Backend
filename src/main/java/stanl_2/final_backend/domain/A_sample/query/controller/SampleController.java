package stanl_2.final_backend.domain.A_sample.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.A_sample.common.response.ResponseMessage;
import stanl_2.final_backend.domain.A_sample.query.dto.SampleDTO;
import stanl_2.final_backend.domain.A_sample.query.service.SampleService;

@RestController(value = "querySampleController")
@RequestMapping("/api/v1/sample")
public class SampleController {

    private final SampleService sampleService;

    @Autowired
    public SampleController(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    /**
     * [GET] http://localhost:7777/api/v1/sample/SAM_000000001
     * */
    @Operation(summary = "샘플 조회 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = ResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("{id}")
    public ResponseEntity<ResponseMessage> getTest(@PathVariable String id){

        SampleDTO sampleDTO = sampleService.selectSampleInfo(id);

        return ResponseEntity.ok(ResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(sampleDTO)
                .build());
    }

    /**
     * [GET] http://localhost:7777/api/v1/sample/detail/1
     * */
    @Operation(summary = "샘플 상세 조회 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = ResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/detail/{id}")
    public ResponseEntity<ResponseMessage> getDetailTest(@PathVariable String id) {

        String name = sampleService.selectSampleName(id);

        return ResponseEntity.ok(ResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(name)
                .build());
    }

}
