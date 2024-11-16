package stanl_2.final_backend.domain.notices.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.A_sample.common.response.SampleResponseMessage;
import stanl_2.final_backend.domain.notices.command.application.dto.NoticeModifyDTO;
import stanl_2.final_backend.domain.notices.command.application.dto.NoticeRegistDTO;
import stanl_2.final_backend.domain.notices.command.application.service.NoticeCommandService;
import stanl_2.final_backend.domain.notices.common.response.NoticeResponseMessage;

@RestController("commandNoticeController")
@RequestMapping("/api/v1/notice")
public class NoticeController {

    private final NoticeCommandService noticeCommandService;

    @Autowired
    public NoticeController(NoticeCommandService noticeCommandService) {
        this.noticeCommandService = noticeCommandService;
    }

    @Operation(summary = "공지사항 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = NoticeResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<NoticeResponseMessage> postNotice(@RequestBody NoticeRegistDTO noticeRegistDTO){
        noticeCommandService.registerNotice(noticeRegistDTO);
        return ResponseEntity.ok(NoticeResponseMessage.builder()
                                                .httpStatus(200)
                                                .msg("성공")
                                                .result(null)
                                                .build());

    }
    @Operation(summary = "공지사항 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = SampleResponseMessage.class))})
    })
    @PutMapping("{id}")
    public ResponseEntity<NoticeResponseMessage> modifyNotice(@PathVariable String id,
                                                              @RequestBody NoticeModifyDTO noticeModifyRequestDTO){

        NoticeModifyDTO noticeModifyDTO = noticeCommandService.modifyNotice(id,noticeModifyRequestDTO);

        return ResponseEntity.ok(NoticeResponseMessage.builder()
                        .httpStatus(200)
                        .msg("성공")
                        .result(noticeModifyDTO)
                        .build());
    }

    @Operation(summary = "공지사항 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = NoticeResponseMessage.class))})
    })
    @DeleteMapping("{id}")
    public ResponseEntity<NoticeResponseMessage> deleteNotice(@PathVariable String id) {

        noticeCommandService.deleteNotice(id);

        return ResponseEntity.ok(NoticeResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(null)
                .build());
    }

}
