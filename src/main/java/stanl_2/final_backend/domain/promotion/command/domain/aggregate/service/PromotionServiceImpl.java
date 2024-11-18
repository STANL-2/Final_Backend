package stanl_2.final_backend.domain.promotion.command.domain.aggregate.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.promotion.command.application.dto.PromotionModifyDTO;
import stanl_2.final_backend.domain.promotion.command.application.dto.PromotionRegistDTO;
import stanl_2.final_backend.domain.promotion.command.application.service.PromotionCommandService;
import stanl_2.final_backend.domain.promotion.command.domain.aggregate.entity.Promotion;
import stanl_2.final_backend.domain.promotion.command.domain.aggregate.repository.PromotionRepository;
import stanl_2.final_backend.domain.promotion.common.exception.PromotionCommonException;
import stanl_2.final_backend.domain.promotion.common.exception.PromotionErrorCode;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service("commandPromotionService")
public class PromotionServiceImpl implements PromotionCommandService {

    private final PromotionRepository promotionRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public PromotionServiceImpl(PromotionRepository promotionRepository, ModelMapper modelMapper) {
        this.promotionRepository = promotionRepository;
        this.modelMapper = modelMapper;
    }
    private String getCurrentTimestamp() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Transactional
    @Override
    public void registerPromotion(PromotionRegistDTO promotionRegistDTO) {
        Promotion promotion =modelMapper.map(promotionRegistDTO,Promotion.class);
        promotionRepository.save(promotion);
    }

    @Transactional
    @Override
    public PromotionModifyDTO modifyPromotion(String promotionId, PromotionModifyDTO promotionModifyDTO) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new PromotionCommonException(PromotionErrorCode.PROMOTION_NOT_FOUND));

        Promotion updatePromotion = modelMapper.map(promotionModifyDTO, Promotion.class);
        updatePromotion.setPromotionId(promotion.getPromotionId());
        updatePromotion.setMemberId(promotion.getMemberId());
        updatePromotion.setCreatedAt(promotion.getCreatedAt());
        updatePromotion.setActive(promotion.getActive());

        promotionRepository.save(updatePromotion);

        PromotionModifyDTO promotionModify = modelMapper.map(updatePromotion,PromotionModifyDTO.class);

        return promotionModify;
    }

    @Transactional
    @Override
    public void deletePromotion(String promotionId) {

        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(()-> new PromotionCommonException(PromotionErrorCode.PROMOTION_NOT_FOUND));

        promotion.setActive(false);
        promotion.setDeletedAt(getCurrentTimestamp());

        promotionRepository.save(promotion);

    }
}
