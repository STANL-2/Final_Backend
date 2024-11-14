package stanl_2.final_backend.domain.notices.command.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.notices.command.application.dto.NoticeModifyDTO;
import stanl_2.final_backend.domain.notices.command.application.dto.NoticeRegistDTO;
import stanl_2.final_backend.domain.notices.command.application.service.NoticeCommandService;
import stanl_2.final_backend.domain.notices.command.domain.aggragate.entity.Notice;
import stanl_2.final_backend.domain.notices.command.domain.repository.NoticeRepository;
import stanl_2.final_backend.domain.notices.common.exception.NoticeCommonException;
import stanl_2.final_backend.domain.notices.common.exception.NoticeErrorCode;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service("commandNoticeService")
public class NoticeCommandServiceImpl implements NoticeCommandService {

    private final NoticeRepository noticeRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public NoticeCommandServiceImpl(NoticeRepository noticeRepository, ModelMapper modelMapper) {
        this.noticeRepository = noticeRepository;
        this.modelMapper = modelMapper;
    }

    private String getCurrentTimestamp() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    @Transactional
    public void registerNotice(NoticeRegistDTO noticeRegistDTO) {
        Notice notice =modelMapper.map(noticeRegistDTO,Notice.class);
        noticeRepository.save(notice);
    }

    @Override
    @Transactional
    public NoticeModifyDTO modifyNotice(String id, NoticeModifyDTO noticeModifyDTO) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeCommonException(NoticeErrorCode.NOTICE_NOT_FOUND));


        Notice updateNotice = modelMapper.map(noticeModifyDTO, Notice.class);
        updateNotice.setNoticeId(notice.getNoticeId());
        updateNotice.setMemberId(notice.getMemberId());
        updateNotice.setCreatedAt(notice.getCreatedAt());
        updateNotice.setActive(notice.getActive());

        noticeRepository.save(updateNotice);

        NoticeModifyDTO noticeModify = modelMapper.map(updateNotice,NoticeModifyDTO.class);

        return noticeModify;
    }

    @Override
    @Transactional
    public void deleteNotice(String id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(()-> new NoticeCommonException(NoticeErrorCode.NOTICE_NOT_FOUND));

        notice.setActive(false);
        notice.setDeletedAt(getCurrentTimestamp());

        noticeRepository.save(notice);
    }
}
