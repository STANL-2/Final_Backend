package stanl_2.final_backend.domain.promotion.command.application.service;

import stanl_2.final_backend.domain.promotion.command.application.dto.PromotionModifyDTO;
import stanl_2.final_backend.domain.promotion.command.application.dto.PromotionRegistDTO;

public interface PromotionCommandService {

    void registerPromotion(PromotionRegistDTO promotionRegistDTO);

    PromotionModifyDTO modifyPromotion(String promotionId, PromotionModifyDTO promotionModifyDTO);

    void deletePromotion(String promotionId);
}
