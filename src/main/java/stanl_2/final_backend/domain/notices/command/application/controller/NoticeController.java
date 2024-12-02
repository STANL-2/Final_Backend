package stanl_2.final_backend.domain.notices.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.notices.command.application.dto.NoticeDeleteDTO;
import stanl_2.final_backend.domain.notices.command.application.dto.NoticeModifyDTO;
import stanl_2.final_backend.domain.notices.command.application.dto.NoticeRegistDTO;
import stanl_2.final_backend.domain.notices.command.application.service.NoticeCommandService;
import stanl_2.final_backend.domain.notices.common.response.NoticeResponseMessage;
import stanl_2.final_backend.domain.s3.command.domain.service.S3FileServiceImpl;

import java.security.Principal;

@RestController("commandNoticeController")
@RequestMapping("/api/v1/notice")
public class NoticeController {

    private final NoticeCommandService noticeCommandService;
    private final AuthQueryService authQueryService;

    private final S3FileServiceImpl s3FileService;
    private final NoticeModifyDTO noticeModifyDTO;

    @Autowired
    public NoticeController(NoticeCommandService noticeCommandService, AuthQueryService authQueryService, S3FileServiceImpl s3FileService, NoticeModifyDTO noticeModifyDTO){
        this.noticeModifyDTO = noticeModifyDTO;
        this.noticeCommandService = noticeCommandService;
        this.authQueryService =authQueryService;
        this.s3FileService = s3FileService;
    }

    @Operation(summary = "공지사항 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = NoticeResponseMessage.class))})
    })
    @PostMapping(value = "")
    public ResponseEntity<NoticeResponseMessage> postNotice(@RequestPart("dto") NoticeRegistDTO noticeRegistDTO, // JSON 데이터
                                                            @RequestPart(value = "file", required = false)  MultipartFile file,
                                                            Principal principal){
        String memberId =authQueryService.selectMemberIdByLoginId(principal.getName());
        noticeRegistDTO.setMemberId(memberId);
        if (file != null && !file.isEmpty()) {
            noticeRegistDTO.setFileUrl(s3FileService.uploadOneFile(file));
            System.out.println("response:1");
        }else if(file==null || file.isEmpty()){
            System.out.println("response:2");
            noticeRegistDTO.setFileUrl(null);
        } else {
            System.out.println("response:3");
            noticeRegistDTO.setFileUrl(null);
        }
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
                                                              @RequestPart("dto") NoticeModifyDTO noticeModifyDTO, // JSON 데이터
                                                              @RequestPart(value = "file", required = false)  MultipartFile file){
        System.out.println("==============================");
        String memberLoginId = principal.getName();
        System.out.println("==============================");
        noticeModifyDTO.setMemberId(memberLoginId);
        System.out.println("==============================");
        noticeModifyDTO.setContent(noticeModifyDTO.getContent());
        if (file != null && !file.isEmpty()) {
            System.out.println("response:1");
            noticeModifyDTO.setFileUrl(s3FileService.uploadOneFile(file));
        }else if(file==null || file.isEmpty()) {
            System.out.println("response:2");
            noticeModifyDTO.setFileUrl(null);
        } else {
            System.out.println("response:3");
            noticeModifyDTO.setFileUrl(s3FileService.uploadOneFile(file));
        }
        noticeCommandService.modifyNotice(noticeId,noticeModifyDTO, principal);

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
