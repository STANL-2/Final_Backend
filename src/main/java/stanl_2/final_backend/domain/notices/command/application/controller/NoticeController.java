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
import stanl_2.final_backend.global.config.datasource.ReplicationRoutingDataSource;

import javax.sql.DataSource;
import java.security.Principal;

@RestController("commandNoticeController")
@RequestMapping("/api/v1/notice")
public class NoticeController {

    private final NoticeCommandService noticeCommandService;
    private final AuthQueryService authQueryService;
    private final ReplicationRoutingDataSource replicationRoutingDataSource;
    private final S3FileServiceImpl s3FileService;

    @Autowired
    public NoticeController(NoticeCommandService noticeCommandService, AuthQueryService authQueryService, S3FileServiceImpl s3FileService,ReplicationRoutingDataSource replicationRoutingDataSource){
        this.noticeCommandService = noticeCommandService;
        this.replicationRoutingDataSource =replicationRoutingDataSource;
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
                                                            @RequestPart("file") MultipartFile file,
                                                            Principal principal){
        String memberId =authQueryService.selectMemberIdByLoginId(principal.getName());
        noticeRegistDTO.setMemberId(memberId);
        noticeRegistDTO.setFileUrl(s3FileService.uploadOneFile(file));
        noticeCommandService.registerNotice(noticeRegistDTO, principal);
        String dbUrl = getCurrentDbUrl();
        System.out.println("Current DB URL: " + dbUrl);
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

    private String getCurrentDbUrl() {
        try {
            // DataSource에서 커넥션을 가져와 URL 확인
            DataSource dataSource = replicationRoutingDataSource;
            return dataSource.unwrap(javax.sql.DataSource.class)
                    .getConnection()
                    .getMetaData()
                    .getURL();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to fetch DB URL";
        }
    }

}
