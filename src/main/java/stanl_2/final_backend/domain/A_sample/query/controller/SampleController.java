package stanl_2.final_backend.domain.A_sample.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.A_sample.common.exception.CommonException;
import stanl_2.final_backend.domain.A_sample.common.response.ResponseMessage;
import stanl_2.final_backend.domain.A_sample.query.service.SampleService;

@RestController(value = "querySampleController")
@RequestMapping("/api/v1/sample")
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;

    /**
     * [GET] http://localhost:7777/api/v1/sample?id=SAM_000001
     * */
    @Operation(summary = "샘플 조회 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = ResponseMessage.class))}),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없음",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("")
    public ResponseEntity<ResponseMessage> getTest(@RequestParam("id") String id) {
        String name = sampleService.findName(id);

        if(name == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ResponseMessage.builder()
                                            .httpStatus(200)
                                            .msg("성공")
                                            .result(name)
                                            .build());
    }

}
