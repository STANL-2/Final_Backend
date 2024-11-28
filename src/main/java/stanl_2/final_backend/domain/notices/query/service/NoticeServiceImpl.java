package stanl_2.final_backend.domain.notices.query.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.notices.query.dto.NoticeDTO;
import stanl_2.final_backend.domain.notices.query.dto.NoticeExcelDownload;
import stanl_2.final_backend.domain.notices.query.dto.SearchDTO;
import stanl_2.final_backend.domain.notices.query.repository.NoticeMapper;
import stanl_2.final_backend.global.excel.ExcelUtilsV1;
import stanl_2.final_backend.global.redis.RedisService;

import java.util.List;


@Service("queryNoticeServiceImpl")
public class NoticeServiceImpl implements NoticeService{
    private final NoticeMapper noticeMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisService redisService;
    private final ExcelUtilsV1 excelUtilsV1;
    @Autowired
    public NoticeServiceImpl(NoticeMapper noticeMapper, RedisTemplate redisTemplate, RedisService redisService, ExcelUtilsV1 excelUtilsV1) {
        this.noticeMapper = noticeMapper;
        this.redisTemplate = redisTemplate;
        this.redisService =redisService;
        this.excelUtilsV1 =excelUtilsV1;
    }

    @Transactional
    @Override
    public Page<NoticeDTO> findNotices(Pageable pageable, SearchDTO searchDTO) {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();
        String cacheKey = "NoticeCache::notices::offset=" + offset + "::size=" + size
                + "::title=" + searchDTO.getTitle()+ "::tag=" + searchDTO.getTag()
                +"::memberid=" + searchDTO.getMemberId()+ "::classification=" + searchDTO.getClassification()
                + "::startDate=" + searchDTO.getStartDate()+ "::endDate=" + searchDTO.getEndDate();

        List<NoticeDTO> notices = (List<NoticeDTO>) redisTemplate.opsForValue().get(cacheKey);
        if (notices == null) {
            System.out.println("데이터베이스에서 데이터 조회 중...");
            notices = noticeMapper.findNotices(offset, size, searchDTO);
            if (notices != null && !notices.isEmpty()) { // 데이터가 있을 때만 캐싱
                redisService.setKeyWithTTL(cacheKey, notices, 30 * 60); // 캐싱 시 동일 키 사용
            }
        } else {
            System.out.println("캐시에서 데이터 조회 중...");
        }
        int totalElements = noticeMapper.findNoticeCount(); // 총 개수 조회
        return new PageImpl<>(notices, pageable, totalElements);
    }
    @Transactional
    @Override
    public NoticeDTO findNotice(String noticeId) {
        NoticeDTO notice = noticeMapper.findNotice(noticeId);
        return notice;
    }
    @Transactional
    @Override
    public void exportNoticesToExcel(HttpServletResponse response) {
        List<NoticeExcelDownload> noticeList = noticeMapper.findNoticesForExcel();

        excelUtilsV1.download(NoticeExcelDownload.class, noticeList, "noticeExcel", response);
    }

}