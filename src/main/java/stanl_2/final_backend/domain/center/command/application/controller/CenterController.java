package stanl_2.final_backend.domain.center.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.center.command.application.dto.request.CenterModifyDTO;
import stanl_2.final_backend.domain.center.command.application.dto.request.CenterRegistDTO;
import stanl_2.final_backend.domain.center.command.application.service.CenterCommandService;
import stanl_2.final_backend.domain.center.common.response.CenterResponseMessage;

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
                    content = {@Content(schema = @Schema(implementation = CenterResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<CenterResponseMessage> postTest(@RequestBody CenterRegistDTO centerRegistDTO){

        centerCommandService.registCenter(centerRegistDTO);

        return ResponseEntity.ok(CenterResponseMessage.builder()
                .httpStatus(200)
                .msg("등록 성공")
                .result(null)
                .build());
    }

    @Operation(summary = "매장 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = CenterResponseMessage.class))})
    })
    @PutMapping("{id}")
    public ResponseEntity<CenterResponseMessage> putTest(@PathVariable("id") String id,
                                     @RequestBody CenterModifyDTO centerModifyDTO){

        centerModifyDTO.setCenterId(id);

        centerCommandService.modifyCenter(centerModifyDTO);

        return ResponseEntity.ok(CenterResponseMessage.builder()
                .httpStatus(200)
                .msg("수정 성공")
                .result(null)
                .build());
    }

    @Operation(summary = "매장 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = CenterResponseMessage.class))})
    })
    @DeleteMapping("{id}")
    public ResponseEntity<CenterResponseMessage> deleteTest(@PathVariable("id") String id){

        centerCommandService.deleteCenter(id);

        return ResponseEntity.ok(CenterResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(null)
                .build());
    }

}
