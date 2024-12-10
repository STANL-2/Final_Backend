package stanl_2.final_backend.domain.promotion.command.application.service;

import stanl_2.final_backend.domain.promotion.command.application.dto.PromotionModifyDTO;
import stanl_2.final_backend.domain.promotion.command.application.dto.PromotionRegistDTO;

import java.security.GeneralSecurityException;
import java.security.Principal;

public interface PromotionCommandService {

    void registerPromotion(PromotionRegistDTO promotionRegistDTO, Principal principal) throws GeneralSecurityException;

    PromotionModifyDTO modifyPromotion(String promotionId, PromotionModifyDTO promotionModifyDTO, Principal principal) throws GeneralSecurityException;

    void deletePromotion(String promotionId, Principal principal) throws GeneralSecurityException;
}
