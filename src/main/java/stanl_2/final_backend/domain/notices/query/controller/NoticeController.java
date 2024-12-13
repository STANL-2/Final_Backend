package stanl_2.final_backend.domain.notices.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.domain.notices.common.response.NoticeResponseMessage;
import stanl_2.final_backend.domain.notices.query.dto.NoticeDTO;
import stanl_2.final_backend.domain.notices.query.dto.SearchDTO;
import stanl_2.final_backend.domain.notices.query.service.NoticeQueryService;

import java.security.GeneralSecurityException;
import java.security.Principal;


@RestController("queryNoticeController")
@RequestMapping("/api/v1/notice")
public class NoticeController {
    private final NoticeQueryService noticeQueryService;
    private final AuthQueryService authQueryService;
    private final MemberQueryService memberQueryService;
    @Autowired
    public NoticeController(NoticeQueryService noticeQueryService,
                            AuthQueryService authQueryService,
                            MemberQueryService memberQueryService) {
        this.noticeQueryService = noticeQueryService;
        this.authQueryService = authQueryService;
        this.memberQueryService = memberQueryService;
    }

    @Operation(summary = "공지사항 조건별 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = NoticeResponseMessage.class))})
    })
    @GetMapping()
    public ResponseEntity<Page<NoticeDTO>> getNotices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String memberId,
            @RequestParam(required = false) String classification,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            Principal principal
    ) throws GeneralSecurityException {
        Pageable pageable = PageRequest.of(page, size);
        SearchDTO searchDTO = new SearchDTO(title, tag, memberId, classification, startDate, endDate);

        Page<NoticeDTO> noticeDTOPage = noticeQueryService.findNotices(pageable, searchDTO);

        return ResponseEntity.ok(noticeDTOPage);
    }

    @Operation(summary = "공지사항 Id로 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = NoticeResponseMessage.class))})
    })
    @GetMapping("{noticeId}")
    public ResponseEntity<NoticeDTO> getNotice(@PathVariable String noticeId){
        NoticeDTO noticeDTO = noticeQueryService.findNotice(noticeId);
        return ResponseEntity.ok(noticeDTO);
    }
    @Operation(summary = "공지사항 엑셀 다운 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 엑셀 다운 테스트 성공",
                    content = {@Content(schema = @Schema(implementation = NoticeResponseMessage.class))}),
    })
    @GetMapping("/excel")
    public void exportNotice(HttpServletResponse response){

        noticeQueryService.exportNoticesToExcel(response);
    }

}
