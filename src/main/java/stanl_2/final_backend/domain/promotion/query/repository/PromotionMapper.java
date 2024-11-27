package stanl_2.final_backend.domain.promotion.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import stanl_2.final_backend.domain.problem.query.dto.ProblemExcelDownload;
import stanl_2.final_backend.domain.promotion.query.dto.PromotionDTO;
import stanl_2.final_backend.domain.promotion.query.dto.PromotionExcelDownload;
import stanl_2.final_backend.domain.promotion.query.dto.PromotionSearchDTO;

import java.util.List;

@Mapper
public interface PromotionMapper {
    List<PromotionDTO> findPromotions(
            @Param("offset") int offset,
            @Param("size") int size,
            @Param("promotionDTO") PromotionSearchDTO promotionSearchDTO
    );
    int findPromotionsCount(@Param("promotionSearchDTO") PromotionSearchDTO promotionSearchDTO);

    PromotionDTO findPromotion(@Param("promotionId") String promotionId);

    List<PromotionExcelDownload> findPromotionsForExcel();
}
