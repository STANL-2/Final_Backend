package stanl_2.final_backend.domain.notices.command.application.service;

import stanl_2.final_backend.domain.notices.command.application.dto.NoticeDeleteDTO;
import stanl_2.final_backend.domain.notices.command.application.dto.NoticeModifyDTO;
import stanl_2.final_backend.domain.notices.command.application.dto.NoticeRegistDTO;
import stanl_2.final_backend.domain.schedule.command.application.dto.ScheduleDeleteDTO;

public interface NoticeCommandService {
    void registerNotice(NoticeRegistDTO noticeRegistDTO);

    NoticeModifyDTO modifyNotice(String id, NoticeModifyDTO noticeModifyDTO);

    void deleteNotice(NoticeDeleteDTO noticeDeleteDTO);
}
