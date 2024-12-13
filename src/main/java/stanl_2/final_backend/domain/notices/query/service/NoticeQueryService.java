package stanl_2.final_backend.domain.notices.query.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stanl_2.final_backend.domain.notices.query.dto.NoticeDTO;
import stanl_2.final_backend.domain.notices.query.dto.SearchDTO;


public interface NoticeQueryService {

    Page<NoticeDTO> findNotices(Pageable pageable, SearchDTO searchDTO);
    NoticeDTO findNotice(String noticeId);
    void exportNoticesToExcel(HttpServletResponse response);

}
