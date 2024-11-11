package stanl_2.final_backend.domain.center.command.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    // 나중에 적용 예정 - swagger 설정
    //    @Operation(summary = "Get center Test")
    //    @ApiResponses(value = {
    //            @ApiResponse(responseCode = "200", description = "성공",
    //                content = {@Content(schema = @Schema(implementation = ResponseMessage.class))})
    //    })

    @PostMapping("")
    public ResponseEntity<?> postTest(@RequestBody CenterRegistRequestDTO centerRegistRequestDTO){

        CenterRegistResponseDTO centerRegistResponseDTO = centerCommandService.registCenter(centerRegistRequestDTO);

        return ResponseEntity.ok(new ResponseMessage(200, "post 성공", centerRegistResponseDTO));
    }

    @PutMapping("")
    public ResponseEntity<?> putTest(){


        return ResponseEntity.ok(new ResponseMessage(200, "put 성공", " "));
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteTest(){


        return ResponseEntity.ok(new ResponseMessage(200, "delete 성공", " "));
    }

}
