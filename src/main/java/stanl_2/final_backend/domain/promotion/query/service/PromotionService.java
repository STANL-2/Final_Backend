package stanl_2.final_backend.domain.promotion.query.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stanl_2.final_backend.domain.promotion.query.dto.PromotionDTO;
import stanl_2.final_backend.domain.promotion.query.dto.PromotionSearchDTO;

public interface PromotionService {
    Page<PromotionDTO> findPromotions(Pageable pageable, PromotionSearchDTO PromotionSearchDTO);

    PromotionDTO findPromotion(String promotionId);

    void exportPromotionToExcel(HttpServletResponse response);
}
