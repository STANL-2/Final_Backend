package stanl_2.final_backend.domain.promotion.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.problem.command.application.dto.ProblemRegistDTO;
import stanl_2.final_backend.domain.promotion.command.application.dto.PromotionModifyDTO;
import stanl_2.final_backend.domain.promotion.command.application.dto.PromotionRegistDTO;
import stanl_2.final_backend.domain.promotion.command.application.service.PromotionCommandService;
import stanl_2.final_backend.domain.promotion.common.response.PromotionResponseMessage;
import stanl_2.final_backend.domain.s3.command.domain.service.S3FileServiceImpl;

import java.security.Principal;

@RestController("commandPromotionController")
@RequestMapping("/api/v1/promotion")
public class PromotionController {
    private final PromotionCommandService promotionCommandService;
    private final AuthQueryService authQueryService;
    private final S3FileServiceImpl s3FileService;

    @Autowired
    public PromotionController(PromotionCommandService promotionCommandService, AuthQueryService authQueryService, S3FileServiceImpl s3FileService) {
        this.promotionCommandService = promotionCommandService;
        this.authQueryService =authQueryService;
        this.s3FileService = s3FileService;
    }

    @Operation(summary = "프로모션 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = PromotionResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<PromotionResponseMessage> postNotice(@RequestPart("promotion") PromotionRegistDTO promotionRegistDTO, // JSON 데이터
                                                               @RequestPart("file") MultipartFile file,
                                                               Principal principal){
        String memberId = authQueryService.selectMemberIdByLoginId(principal.getName());
        promotionRegistDTO.setMemberId(memberId);
        promotionRegistDTO.setFileUrl(s3FileService.uploadOneFile(file));
        promotionCommandService.registerPromotion(promotionRegistDTO,principal);
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
                                                              @RequestBody PromotionModifyDTO promotionModifyRequestDTO, Principal principal){

        PromotionModifyDTO promotionModifyDTO = promotionCommandService.modifyPromotion(promotionId,promotionModifyRequestDTO,principal);

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
    public ResponseEntity<PromotionResponseMessage> deleteNotice(@PathVariable String promotionId, Principal principal) {

        promotionCommandService.deletePromotion(promotionId,principal);

        return ResponseEntity.ok(PromotionResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(null)
                .build());
    }
}
