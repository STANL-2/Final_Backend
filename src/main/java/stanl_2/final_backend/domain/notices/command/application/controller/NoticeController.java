package stanl_2.final_backend.domain.notices.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.notices.command.application.dto.NoticeModifyDTO;
import stanl_2.final_backend.domain.notices.command.application.dto.NoticeRegistDTO;
import stanl_2.final_backend.domain.notices.command.application.service.NoticeCommandService;
import stanl_2.final_backend.domain.notices.common.response.NoticeResponseMessage;

import java.security.Principal;

@RestController("commandNoticeController")
@RequestMapping("/api/v1/notice")
public class NoticeController {

    private final NoticeCommandService noticeCommandService;
    private final AuthQueryService authQueryService;

    @Autowired
    public NoticeController(NoticeCommandService noticeCommandService, AuthQueryService authQueryService){
        this.noticeCommandService = noticeCommandService;
        this.authQueryService =authQueryService;
    }

    @Operation(summary = "공지사항 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = NoticeResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<NoticeResponseMessage> postNotice(@RequestBody NoticeRegistDTO noticeRegistDTO, Principal principal){
        String memberId =authQueryService.selectMemberIdByLoginId(principal.getName());
        noticeRegistDTO.setMemberId(memberId);
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
                    content = {@Content(schema = @Schema(implementation = NoticeResponseMessage.class))})
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
