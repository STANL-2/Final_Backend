package stanl_2.final_backend.domain.A_sample.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.A_sample.command.application.dto.request.PostRequestDTO;
import stanl_2.final_backend.domain.A_sample.command.application.dto.request.PutRequestDTO;
import stanl_2.final_backend.domain.A_sample.command.application.dto.response.DeleteResponseDTO;
import stanl_2.final_backend.domain.A_sample.command.application.dto.response.PostResponseDTO;
import stanl_2.final_backend.domain.A_sample.command.application.dto.response.PutResponseDTO;
import stanl_2.final_backend.domain.A_sample.command.application.service.SampleService;
import stanl_2.final_backend.domain.A_sample.common.response.ResponseMessage;

@RestController("commandSampleController")
@RequestMapping("/api/v1/sample")
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;

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
                        content = {@Content(schema = @Schema(implementation = ResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<ResponseMessage> postTest(@RequestBody PostRequestDTO postRequestDTO) {

        PostResponseDTO postResponseDTO = sampleService.register(postRequestDTO);

        if(postResponseDTO == null) {
            ResponseEntity.badRequest();
        }

        return ResponseEntity.ok(ResponseMessage.builder()
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
                    content = {@Content(schema = @Schema(implementation = ResponseMessage.class))})
    })
    @PutMapping("")
    public ResponseEntity<ResponseMessage> putTest(@RequestParam("mem_id") String id
            , @RequestBody PutRequestDTO putRequestDTO) {

        PutResponseDTO putResponseDTO = sampleService.modify(id, putRequestDTO);

        return ResponseEntity.ok(ResponseMessage.builder()
                                                .httpStatus(200)
                                                .msg("성공")
                                                .result(putResponseDTO)
                                                .build());
    }

    /**
     * [DELETE] http://localhost:7777/api/v1/sample?mem_id=SAM_000001
     * */
    @Operation(summary = "샘플 삭제 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = ResponseMessage.class))})
    })
    @DeleteMapping("")
    public ResponseEntity<ResponseMessage> deleteTest(@RequestParam("mem_id") String id) {

        DeleteResponseDTO deleteResponseDTO = sampleService.remove(id);

        // 원래 잘 active 값이 변경되었는지 확인
        if(deleteResponseDTO == null) {
            ResponseEntity.badRequest();
        }

        return ResponseEntity.ok(ResponseMessage.builder()
                                                .httpStatus(200)
                                                .msg("성공")
                                                .result(null)
                                                .build());
    }


}
