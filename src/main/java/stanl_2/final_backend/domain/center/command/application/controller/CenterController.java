package stanl_2.final_backend.domain.center.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.A_sample.command.application.dto.response.SampleModifyResponseDTO;
import stanl_2.final_backend.domain.center.command.application.dto.request.CenterRegistRequestDTO;
import stanl_2.final_backend.domain.center.command.application.dto.response.CenterRegistResponseDTO;
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
                    content = {@Content(schema = @Schema(implementation = stanl_2.final_backend.domain.A_sample.common.response.ResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<?> postTest(@RequestBody CenterRegistRequestDTO centerRegistRequestDTO){
        /* 설명. memberId 토큰으로 받는 것 고려 */

        CenterRegistResponseDTO centerRegistResponseDTO = centerCommandService.registCenter(centerRegistRequestDTO);

        centerRegistRequestDTO.setId(id);
        centerCommandService.registerCenter(centerRegistRequestDTO);

        return ResponseEntity.ok(stanl_2.final_backend.domain.A_sample.common.response.ResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(null)
                .build());
    }

    @Operation(summary = "매장 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = stanl_2.final_backend.domain.A_sample.common.response.ResponseMessage.class))})
    })
    @PutMapping("{id}")
    public ResponseEntity<?> putTest(){

        sampleModifyRequestDTO.setId(id);
        SampleModifyResponseDTO sampleModifyResponseDTO = sampleCommandService.modifySample(id, sampleModifyRequestDTO);

        return ResponseEntity.ok(stanl_2.final_backend.domain.A_sample.common.response.ResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(sampleModifyResponseDTO)
                .build());    }

    @Operation(summary = "샘플 삭제 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = stanl_2.final_backend.domain.A_sample.common.response.ResponseMessage.class))})
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTest(){


        return ResponseEntity.ok(new ResponseMessage(200, "delete 성공", " "));
    }

}
