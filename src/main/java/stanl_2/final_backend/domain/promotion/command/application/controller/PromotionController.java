package stanl_2.final_backend.domain.promotion.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stanl_2.final_backend.domain.promotion.command.application.dto.PromotionModifyDTO;
import stanl_2.final_backend.domain.promotion.command.application.dto.PromotionRegistDTO;
import stanl_2.final_backend.domain.promotion.command.application.service.PromotionCommandService;
import stanl_2.final_backend.domain.promotion.common.response.PromotionResponseMessage;

@RestController("commandPromotionController")
@RequestMapping("/api/v1/promotion")
public class PromotionController {
    private final PromotionCommandService promotionCommandService;

    @Autowired
    public PromotionController(PromotionCommandService promotionCommandService) {
        this.promotionCommandService = promotionCommandService;
    }

    @Operation(summary = "프로모션 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = PromotionResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<PromotionResponseMessage> postNotice(@RequestBody PromotionRegistDTO prmotionRegistDTO){
        promotionCommandService.registerPromotion(prmotionRegistDTO);
        return ResponseEntity.ok(PromotionResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(null)
                .build());

    }
    @Operation(summary = "프로모션 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = PromotionResponseMessage.class))})
    })
    @PutMapping("{promotionId}")
    public ResponseEntity<PromotionResponseMessage> modifyNotice(@PathVariable String promotionId,
                                                              @RequestBody PromotionModifyDTO promotionModifyRequestDTO){

        PromotionModifyDTO promotionModifyDTO = promotionCommandService.modifyPromotion(promotionId,promotionModifyRequestDTO);

        return ResponseEntity.ok(PromotionResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(promotionModifyDTO)
                .build());
    }

    @Operation(summary = "프로모션 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = PromotionResponseMessage.class))})
    })
    @DeleteMapping("{promotionId}")
    public ResponseEntity<PromotionResponseMessage> deleteNotice(@PathVariable String promotionId) {

        promotionCommandService.deletePromotion(promotionId);

        return ResponseEntity.ok(PromotionResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(null)
                .build());
    }
}
