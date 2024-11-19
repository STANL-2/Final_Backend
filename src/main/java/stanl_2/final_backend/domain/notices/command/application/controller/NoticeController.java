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
import stanl_2.final_backend.domain.notices.command.application.dto.NoticeDeleteDTO;
import stanl_2.final_backend.domain.notices.command.application.dto.NoticeModifyDTO;
import stanl_2.final_backend.domain.notices.command.application.dto.NoticeRegistDTO;
import stanl_2.final_backend.domain.notices.command.application.service.NoticeCommandService;
import stanl_2.final_backend.domain.notices.common.response.NoticeResponseMessage;
import stanl_2.final_backend.domain.schedule.command.application.dto.ScheduleDeleteDTO;

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
        System.out.println("memberId"+memberId);
        noticeRegistDTO.setMemberId(memberId);
        noticeCommandService.registerNotice(noticeRegistDTO, principal);
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
    @PutMapping("{noticeId}")
    public ResponseEntity<NoticeResponseMessage> modifyNotice(Principal principal,
                                                              @PathVariable String noticeId,
                                                              @RequestBody NoticeModifyDTO noticeModifyRequestDTO){
        String memberLoginId = principal.getName();
        noticeModifyRequestDTO.setMemberLoginId(memberLoginId);
        noticeModifyRequestDTO.setNoticeId(noticeId);

        NoticeModifyDTO noticeModifyDTO = noticeCommandService.modifyNotice(noticeId,noticeModifyRequestDTO,principal);

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
    @DeleteMapping("{noticeId}")
    public ResponseEntity<NoticeResponseMessage> deleteNotice(Principal principal,
                                                              @PathVariable String noticeId) {

        String memberLoginId = principal.getName();
        NoticeDeleteDTO noticeDeleteDTO = new NoticeDeleteDTO();
        noticeDeleteDTO.setMemberLoginId(memberLoginId);
        noticeDeleteDTO.setNoticeId(noticeId);

        noticeCommandService.deleteNotice(noticeDeleteDTO,principal);

        return ResponseEntity.ok(NoticeResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(null)
                .build());
    }

}
