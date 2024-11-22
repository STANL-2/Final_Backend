package stanl_2.final_backend.domain.notices.query.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stanl_2.final_backend.domain.notices.query.dto.NoticeDTO;
import stanl_2.final_backend.domain.notices.query.dto.SearchDTO;


public interface NoticeService {

    Page<NoticeDTO> findNotices(Pageable pageable, SearchDTO searchDTO);
    NoticeDTO findNotice(String noticeId);

}
