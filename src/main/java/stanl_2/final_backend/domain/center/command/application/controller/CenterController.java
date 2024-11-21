package stanl_2.final_backend.domain.center.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.A_sample.common.response.SampleResponseMessage;
import stanl_2.final_backend.domain.center.command.application.dto.request.CenterModifyRequestDTO;
import stanl_2.final_backend.domain.center.command.application.dto.request.CenterRegistRequestDTO;
import stanl_2.final_backend.domain.center.command.application.service.CenterCommandService;
import stanl_2.final_backend.domain.center.common.response.ResponseMessage;

@RestController("commandCenterController")
@RequestMapping("/api/v1/center")
public class CenterController {

    private final CenterCommandService centerCommandService;

    @Autowired
    public CenterController(CenterCommandService centerCommandService) {
        this.centerCommandService = centerCommandService;
    }

    @Operation(summary = "매장 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SampleResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<?> postTest(@RequestBody CenterRegistRequestDTO centerRegistRequestDTO){
        /* 설명. memberId 토큰으로 받는 것 고려 */

        centerCommandService.registCenter(centerRegistRequestDTO);


        return ResponseEntity.ok(ResponseMessage.builder()
                .httpStatus(200)
                .msg("등록 성공")
                .result(null)
                .build());
    }

    @Operation(summary = "매장 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SampleResponseMessage.class))})
    })
    @PutMapping("{id}")
    public ResponseEntity<?> putTest(@PathVariable("id") String id,
                                     @RequestBody CenterModifyRequestDTO centerModifyRequestDTO){

        centerCommandService.modifyCenter(id, centerModifyRequestDTO);

        return ResponseEntity.ok(ResponseMessage.builder()
                .httpStatus(200)
                .msg("수정 성공")
                .result(null)
                .build());
    }

    @Operation(summary = "매장 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SampleResponseMessage.class))})
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTest(@PathVariable("id") String id){

        centerCommandService.deleteCenter(id);

        return ResponseEntity.ok(ResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(null)
                .build());
    }

}
