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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.A_sample.common.response.SampleResponseMessage;
import stanl_2.final_backend.domain.notices.common.response.NoticeResponseMessage;
import stanl_2.final_backend.domain.notices.query.dto.NoticeDTO;
import stanl_2.final_backend.domain.notices.query.dto.SearchDTO;
import stanl_2.final_backend.domain.notices.query.service.NoticeService;
import stanl_2.final_backend.global.config.datasource.ReplicationRoutingDataSource;

import javax.sql.DataSource;

import static org.springframework.transaction.support.TransactionSynchronizationManager.isCurrentTransactionReadOnly;

/* 설명. Service단 설정이 완료되었다면
    String dbUrl = getCurrentDbUrl();
    System.out.println("Current DB URL: " + dbUrl);
    를 통해 현재 접근하고 있는 DB를 조회할 경우와 DB의 값을 등록,수정,삭제할 경우의
    url 주소를 비교해 본다.
*/
@RestController("queryNoticeController")
@RequestMapping("/api/v1/notice")
public class NoticeController {
    private final NoticeService noticeService;

    private final ReplicationRoutingDataSource replicationRoutingDataSource;

    @Autowired
    public NoticeController(NoticeService noticeService, ReplicationRoutingDataSource replicationRoutingDataSource) {
        this.noticeService = noticeService;
        this.replicationRoutingDataSource= replicationRoutingDataSource;
    }

    @Operation(summary = "공지사항 조건별 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = NoticeResponseMessage.class))})
    })
    @Transactional(readOnly = true)
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
        SearchDTO searchDTO = new SearchDTO(title,tag,memberId,classification,startDate,endDate);
        System.out.println("1.Transaction ReadOnly: " + isCurrentTransactionReadOnly());
        Page<NoticeDTO> noticeDTOPage = noticeService.findNotices(pageable,searchDTO);
        System.out.println("6.Transaction ReadOnly: " + isCurrentTransactionReadOnly());
        String dbUrl = getCurrentDbUrl();
        System.out.println("Current DB URL: " + dbUrl);
        return ResponseEntity.ok(noticeDTOPage);
    }

    @Operation(summary = "공지사항 Id로 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = NoticeResponseMessage.class))})
    })
    @Transactional(readOnly = true)
    @GetMapping("{noticeId}")
    public ResponseEntity<NoticeDTO> getNotice(@PathVariable String noticeId){
        NoticeDTO noticeDTO = noticeService.findNotice(noticeId);
        return ResponseEntity.ok(noticeDTO);
    }
    @Operation(summary = "공지사항 엑셀 다운 테스트")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 엑셀 다운 테스트 성공",
                    content = {@Content(schema = @Schema(implementation = NoticeResponseMessage.class))}),
    })
    @GetMapping("/excel")
    public void exportNotice(HttpServletResponse response){

        noticeService.exportNoticesToExcel(response);
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
