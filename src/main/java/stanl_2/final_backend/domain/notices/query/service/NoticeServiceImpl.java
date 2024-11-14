package stanl_2.final_backend.domain.notices.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.notices.query.dto.NoticeDTO;
import stanl_2.final_backend.domain.notices.query.repository.NoticeMapper;

import java.util.List;


@Service("queryNoticeServiceImpl")
public class NoticeServiceImpl implements NoticeService{
    private final NoticeMapper noticeMapper;

    @Autowired
    public NoticeServiceImpl(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }


    @Override
    public Page<NoticeDTO> findAllNotices(Pageable pageable) {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();
        List<NoticeDTO> notices = noticeMapper.findAllNotices(offset,size);
        int totalElements = noticeMapper.findNoticeCount(); // 총 개수 조회

        return new PageImpl<>(notices, pageable, totalElements);
    }

}
