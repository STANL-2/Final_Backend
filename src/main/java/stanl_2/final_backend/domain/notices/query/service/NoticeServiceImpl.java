package stanl_2.final_backend.domain.notices.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.notices.query.dto.NoticeDTO;
import stanl_2.final_backend.domain.notices.query.dto.SearchDTO;
import stanl_2.final_backend.domain.notices.query.repository.NoticeMapper;

import java.util.List;


@Service("queryNoticeServiceImpl")
public class NoticeServiceImpl implements NoticeService{
    private final NoticeMapper noticeMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public NoticeServiceImpl(NoticeMapper noticeMapper, RedisTemplate redisTemplate) {
        this.noticeMapper = noticeMapper;
        this.redisTemplate = redisTemplate;
    }

    @Transactional
    @Override
    public Page<NoticeDTO> findAllNotices(Pageable pageable) {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();
        String cacheKey = "myCache::notices::offset=" + offset + "::size=" + size;

        List<NoticeDTO> notices = (List<NoticeDTO>) redisTemplate.opsForValue().get(cacheKey);

        // 캐시에 데이터가 없다면 DB에서 조회하고 캐시에 저장
        if (notices == null) {
            System.out.println("데이터베이스에서 데이터 조회 중...");
            notices = noticeMapper.findAllNotices(offset, size);
            redisTemplate.opsForValue().set(cacheKey, notices);
        } else {
            System.out.println("캐시에서 데이터 조회 중...");
        }
        int totalElements = noticeMapper.findNoticeCount(); // 총 개수 조회
        return new PageImpl<>(notices, pageable, totalElements);
    }

    @Transactional
    @Override
    public Page<NoticeDTO> findNotices(Pageable pageable, SearchDTO searchDTO) {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();
        List<NoticeDTO> notices = noticeMapper.findNotices(offset,size,searchDTO);
        Integer count = noticeMapper.findNoticesCount(searchDTO);
        int noticeCount = (count != null) ?  noticeMapper.findNoticesCount(searchDTO) : 0;

        return new PageImpl<>(notices, pageable, noticeCount);
    }

    @Override
    public NoticeDTO findNotice(String noticeId) {
        NoticeDTO notice = noticeMapper.findNotice(noticeId);
        return notice;
    }

}
