package stanl_2.final_backend.domain.notices.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.notices.common.response.NoticeResponseMessage;
import stanl_2.final_backend.domain.notices.query.dto.NoticeDTO;
import stanl_2.final_backend.domain.notices.query.dto.SearchDTO;
import stanl_2.final_backend.domain.notices.query.service.NoticeService;

@RestController("queryNoticeController")
@RequestMapping("/api/v1/notice")
public class NoticeController {
    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

//    @Operation(summary = "공지사항 전체 조회")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "성공",
//                    content = {@Content(schema = @Schema(implementation = NoticeResponseMessage.class))})
//    })
//    @GetMapping("/all")
//    public ResponseEntity<Page<NoticeDTO>> getAllNotices(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size
//    ) {
//        // PageRequest를 사용하여 페이지 번호와 페이지 크기를 설정
//        Pageable pageable = PageRequest.of(page, size);
//
//        // 공지사항 목록 조회
//        Page<NoticeDTO> noticeDTOPage = noticeService.findAllNotices(pageable);
//
//        return ResponseEntity.ok(noticeDTOPage);
//    }

    @Operation(summary = "공지사항 조건별 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = NoticeResponseMessage.class))})
    })
    @GetMapping
    public ResponseEntity<Page<NoticeDTO>> getNotices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String memberId,
            @RequestParam(required = false) String classification,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate

    ) {
        Pageable pageable = PageRequest.of(page, size);
        SearchDTO searchDTO = new SearchDTO(title, tag, classification, memberId, startDate, endDate);
        Page<NoticeDTO> noticeDTOPage = noticeService.findNotices(pageable,searchDTO);

        return ResponseEntity.ok(noticeDTOPage);
    }

    @Operation(summary = "공지사항 Id로 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = NoticeResponseMessage.class))})
    })
    @GetMapping("{noticeId}")
    public ResponseEntity<NoticeDTO> getNotice(@PathVariable String noticeId){
        NoticeDTO noticeDTO = noticeService.findNotice(noticeId);
        return ResponseEntity.ok(noticeDTO);
    }


}
