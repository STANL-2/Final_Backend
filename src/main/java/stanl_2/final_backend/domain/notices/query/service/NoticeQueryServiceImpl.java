package stanl_2.final_backend.domain.notices.query.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.domain.notices.query.dto.NoticeDTO;
import stanl_2.final_backend.domain.notices.query.dto.NoticeExcelDownload;
import stanl_2.final_backend.domain.notices.query.dto.SearchDTO;
import stanl_2.final_backend.domain.notices.query.repository.NoticeMapper;
import stanl_2.final_backend.domain.sales_history.common.exception.SalesHistoryCommonException;
import stanl_2.final_backend.domain.sales_history.common.exception.SalesHistoryErrorCode;
import stanl_2.final_backend.global.excel.ExcelUtilsV1;
import stanl_2.final_backend.global.redis.RedisService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.List;

import static org.springframework.transaction.support.TransactionSynchronizationManager.isCurrentTransactionReadOnly;
/* 설명. 조회의 경우 readOnly= true 를 설정해 readOnlyDB에 접근을 하도록 만든다.
    "Transaction ReadOnly: " + isCurrentTransactionReadOnly();를 통해
    현재의 트랜젝션이 ReadOnly 상태인지 확인할 수 있다.
    (readOnly=false)나 default로 설정된 메소드를 호출할 경우 readOnly설정이 풀릴 수도 있음
    이런 경우, Controller의 api에 (readOnly=true)로 설정해 본다.
*/

@Transactional(readOnly = true)
@Slf4j
@Service("queryNoticeServiceImpl")
public class NoticeQueryServiceImpl implements NoticeQueryService {
    private final NoticeMapper noticeMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisService redisService;
    private final ExcelUtilsV1 excelUtilsV1;
    private final MemberQueryService memberQueryService;

    private final DataSource dataSource;
    @Autowired
    public NoticeQueryServiceImpl(NoticeMapper noticeMapper, RedisTemplate redisTemplate, RedisService redisService,
                                  ExcelUtilsV1 excelUtilsV1, MemberQueryService memberQueryService, DataSource dataSource) {
        this.noticeMapper = noticeMapper;
        this.redisTemplate = redisTemplate;
        this.redisService =redisService;
        this.excelUtilsV1 =excelUtilsV1;
        this.memberQueryService =memberQueryService;
        this.dataSource = dataSource;
    }

    private String getDatabaseUrl() {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            return metaData.getURL();
        } catch (Exception e) {
            throw new RuntimeException("DB URL 조회 실패", e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Page<NoticeDTO> findNotices(Pageable pageable, SearchDTO searchDTO) {
        log.info("readOnly = true 적용 시 DB URL: " + getDatabaseUrl());
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();
        System.out.println("3.Transaction ReadOnly: " + isCurrentTransactionReadOnly());
        String cacheKey = "NoticeCache::notices::offset=" + offset + "::size=" + size
                + "::title=" + searchDTO.getTitle()+ "::tag=" + searchDTO.getTag()
                +"::memberid=" + searchDTO.getMemberId()+ "::classification=" + searchDTO.getClassification()
                + "::startDate=" + searchDTO.getStartDate()+ "::endDate=" + searchDTO.getEndDate();
        List<NoticeDTO> notices = (List<NoticeDTO>) redisTemplate.opsForValue().get(cacheKey);
        if (notices == null) {
            log.info("데이터베이스에서 데이터 조회 중...");
            notices = noticeMapper.findNotices(offset, size, searchDTO);
            if (notices != null && !notices.isEmpty()) { // 데이터가 있을 때만 캐싱
                redisService.setKeyWithTTL(cacheKey, notices, 30 * 60); // 캐싱 시 동일 키 사용
            }
        } else {
            log.info("캐시에서 데이터 조회 중...");
        }
        notices.forEach(notice -> {
            try {
            } catch (Exception e) {
                throw new SalesHistoryCommonException(SalesHistoryErrorCode.MEMBER_NOT_FOUND);
            }
        });
        int totalElements = noticeMapper.findNoticeCount(); // 총 개수 조회
        return new PageImpl<>(notices, pageable, totalElements);
    }
    @Transactional
    @Override
    public NoticeDTO findNotice(String noticeId) {
        NoticeDTO notice = noticeMapper.findNotice(noticeId);
        log.info("Default DB URL: " + getDatabaseUrl());
        return notice;
    }
    @Transactional(readOnly = true)
    @Override
    public void exportNoticesToExcel(HttpServletResponse response) {
        List<NoticeExcelDownload> noticeList = noticeMapper.findNoticesForExcel();

        excelUtilsV1.download(NoticeExcelDownload.class, noticeList, "noticeExcel", response);
    }


}