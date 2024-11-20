package stanl_2.final_backend.domain.promotion.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stanl_2.final_backend.domain.promotion.common.response.PromotionResponseMessage;
import stanl_2.final_backend.domain.promotion.query.dto.PromotionDTO;
import stanl_2.final_backend.domain.promotion.query.dto.PromotionSearchDTO;
import stanl_2.final_backend.domain.promotion.query.service.PromotionService;

@RestController("queryPromotionController")
@RequestMapping("/api/v1/promotion")
public class PromotionController {
    private final PromotionService promotionService;

    @Autowired
    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @Operation(summary = "프로모션 조건별 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = PromotionResponseMessage.class))})
    })
    @GetMapping
    public ResponseEntity<Page<PromotionDTO>> getPromotions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String memberId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    )
    {
        Pageable pageable = PageRequest.of(page, size);
        PromotionSearchDTO promotionsearchDTO = new PromotionSearchDTO(title, memberId, startDate, endDate);
        Page<PromotionDTO> promotionDTOPage = promotionService.findPromotions(pageable,promotionsearchDTO);

        return ResponseEntity.ok(promotionDTOPage);
    }
}


