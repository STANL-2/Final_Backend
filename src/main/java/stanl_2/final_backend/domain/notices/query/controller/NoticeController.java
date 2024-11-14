package stanl_2.final_backend.domain.notices.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.notices.query.dto.NoticeDTO;
import stanl_2.final_backend.domain.notices.query.service.NoticeService;

@RestController("queryNoticeController")
@RequestMapping("/api/v1/notice")
public class NoticeController {
    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping
    public ResponseEntity<Page<NoticeDTO>> getNotices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        // PageRequest를 사용하여 페이지 번호와 페이지 크기를 설정
        Pageable pageable = PageRequest.of(page, size);

        // 공지사항 목록 조회
        Page<NoticeDTO> noticeDTOPage = noticeService.findAllNotices(pageable);

        return ResponseEntity.ok(noticeDTOPage);
    }
}
