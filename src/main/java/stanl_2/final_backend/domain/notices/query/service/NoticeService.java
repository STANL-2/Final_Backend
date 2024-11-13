package stanl_2.final_backend.domain.notices.query.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stanl_2.final_backend.domain.notices.query.dto.NoticeDTO;

import java.util.List;

public interface NoticeService {

    Page<NoticeDTO> findAllNotices(Pageable pageable);

}
