package stanl_2.final_backend.domain.notices.command.application.service;

import stanl_2.final_backend.domain.notices.command.application.dto.NoticeDeleteDTO;
import stanl_2.final_backend.domain.notices.command.application.dto.NoticeModifyDTO;
import stanl_2.final_backend.domain.notices.command.application.dto.NoticeRegistDTO;

import java.security.GeneralSecurityException;
import java.security.Principal;

public interface NoticeCommandService {
    void registerNotice(NoticeRegistDTO noticeRegistDTO, Principal principal) throws GeneralSecurityException;

    NoticeModifyDTO modifyNotice(String id, NoticeModifyDTO noticeModifyDTO,Principal principal) throws GeneralSecurityException;

    void deleteNotice(NoticeDeleteDTO noticeDeleteDTO,Principal principal);
}
