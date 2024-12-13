package stanl_2.final_backend.domain.promotion.query.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.domain.problem.query.dto.ProblemDTO;
import stanl_2.final_backend.domain.problem.query.dto.ProblemExcelDownload;
import stanl_2.final_backend.domain.promotion.query.dto.PromotionDTO;
import stanl_2.final_backend.domain.promotion.query.dto.PromotionExcelDownload;
import stanl_2.final_backend.domain.promotion.query.dto.PromotionSearchDTO;
import stanl_2.final_backend.domain.promotion.query.repository.PromotionMapper;
import stanl_2.final_backend.domain.sales_history.common.exception.SalesHistoryCommonException;
import stanl_2.final_backend.domain.sales_history.common.exception.SalesHistoryErrorCode;
import stanl_2.final_backend.global.excel.ExcelUtilsV1;
import stanl_2.final_backend.global.redis.RedisService;

import java.util.List;

@Service("queryPromoteServiceImpl")
public class PromotionServiceImpl implements PromotionService{
    private final PromotionMapper promotionMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisService redisService;
    private final ExcelUtilsV1 excelUtilsV1;
    private final MemberQueryService memberQueryService;
    @Autowired
    public PromotionServiceImpl(PromotionMapper promotionMapper, RedisTemplate<String, Object> redisTemplate, RedisService redisService, ExcelUtilsV1 excelUtilsV1, MemberQueryService memberQueryService) {
        this.promotionMapper = promotionMapper;
        this.redisTemplate = redisTemplate;
        this.memberQueryService = memberQueryService;
        this.redisService = redisService;
        this.excelUtilsV1 =excelUtilsV1;
    }

    @Transactional
    @Override
    public Page<PromotionDTO> findPromotions(Pageable pageable, PromotionSearchDTO promotionSearchDTO) {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();
        String cacheKey = "PromotionCache::offset=" + offset + "::size=" + size +"::title="+ promotionSearchDTO.getTitle()+"::memberId="+ promotionSearchDTO.getMemberId()+"::startDate="+ promotionSearchDTO.getStartDate()+"::endDate="+ promotionSearchDTO.getEndDate();
        List<PromotionDTO> promotions = (List<PromotionDTO>) redisTemplate.opsForValue().get(cacheKey);
        if (promotions == null) {
            System.out.println("데이터베이스에서 프로모션 데이터 조회 중...");
            promotions = promotionMapper.findPromotions(offset, size, promotionSearchDTO);
            if (promotions != null && !promotions.isEmpty())
                redisService.setKeyWithTTL(cacheKey, promotions, 30 * 60);
        } else {
            System.out.println("캐시에서 프로모션 데이터 조회 중...");
        }
        promotions.forEach(promotion -> {
            try {
            } catch (Exception e) {
                throw new SalesHistoryCommonException(SalesHistoryErrorCode.MEMBER_NOT_FOUND);
            }
        });
        Integer totalElements = promotionMapper.findPromotionsCount(promotionSearchDTO);
        return new PageImpl<>(promotions, pageable, totalElements);
    }

    @Transactional
    @Override
    public PromotionDTO findPromotion(String promotionId) {
        PromotionDTO promotionDTO = promotionMapper.findPromotion(promotionId);
        return promotionDTO;
    }

    @Transactional
    @Override
    public void exportPromotionToExcel(HttpServletResponse response) {
        List<PromotionExcelDownload> promotionList = promotionMapper.findPromotionsForExcel();

        excelUtilsV1.download(PromotionExcelDownload.class, promotionList, "promotionExcel", response);
    }
}
