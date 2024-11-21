package stanl_2.final_backend.domain.promotion.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.promotion.query.dto.PromotionDTO;
import stanl_2.final_backend.domain.promotion.query.dto.PromotionSearchDTO;
import stanl_2.final_backend.domain.promotion.query.repository.PromotionMapper;

import java.util.List;

@Service("queryPromoteServiceImpl")
public class PromotionServiceImpl implements PromotionService{
    private final PromotionMapper promotionMapper;
    @Autowired
    public PromotionServiceImpl(PromotionMapper promotionMapper) {
        this.promotionMapper = promotionMapper;
    }

    @Transactional
    @Override
    public Page<PromotionDTO> findPromotions(Pageable pageable, PromotionSearchDTO promotionSearchDTO) {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();
        List<PromotionDTO> promotions = promotionMapper.findPromotions(offset,size,promotionSearchDTO);
        Integer count = promotionMapper.findPromotionsCount(promotionSearchDTO);
        int totalCount = (count != null) ?  promotionMapper.findPromotionsCount(promotionSearchDTO) : 0;

        return new PageImpl<>(promotions, pageable, totalCount);
    }

    @Override
    public PromotionDTO findPromotion(String promotionId) {
        PromotionDTO promotionDTO = promotionMapper.findPromotion(promotionId);
        return promotionDTO;
    }
}
