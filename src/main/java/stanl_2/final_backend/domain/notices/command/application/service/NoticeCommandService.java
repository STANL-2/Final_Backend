package stanl_2.final_backend.domain.notices.command.application.service;

import stanl_2.final_backend.domain.notices.command.application.dto.NoticeModifyDTO;
import stanl_2.final_backend.domain.notices.command.application.dto.NoticeRegistDTO;

public interface NoticeCommandService {
    void registerNotice(NoticeRegistDTO noticeRegistDTO);

    NoticeModifyDTO modifyNotice(String title, String tag, String classification, String content, String updatedAt);

    void deleteNotice(String id);
}
