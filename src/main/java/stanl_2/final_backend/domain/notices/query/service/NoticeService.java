package stanl_2.final_backend.domain.notices.query.service;


import stanl_2.final_backend.domain.notices.query.dto.NoticeDTO;

import java.util.List;

public interface NoticeService {

    List<NoticeDTO> findAllNotices();

}
