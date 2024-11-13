package stanl_2.final_backend.domain.notices.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.notices.command.domain.repository.NoticeRepository;
import stanl_2.final_backend.domain.notices.query.dto.NoticeDTO;
import stanl_2.final_backend.domain.notices.query.repository.NoticeMapper;


@Service("queryNoticeServiceImpl")
public class NoticeServiceImpl implements NoticeService{
    private final NoticeRepository noticeRepository;
    private final NoticeMapper noticeMapper;

    @Autowired
    public NoticeServiceImpl(NoticeMapper noticeMapper, NoticeRepository noticeRepository) {
        this.noticeMapper = noticeMapper;
        this.noticeRepository = noticeRepository;
    }


    @Override
    public Page<NoticeDTO> findAllNotices(Pageable pageable) {
        return noticeRepository.findAll(pageable)
                .map(NoticeDTO::new);
    }
}
