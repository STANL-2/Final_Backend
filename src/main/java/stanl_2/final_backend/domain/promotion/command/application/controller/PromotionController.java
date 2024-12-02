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
    private final PromotionModifyDTO promotionModifyDTO;

    @Autowired
    public PromotionController(PromotionCommandService promotionCommandService, AuthQueryService authQueryService, S3FileServiceImpl s3FileService, PromotionModifyDTO promotionModifyDTO) {
        this.promotionCommandService = promotionCommandService;
        this.authQueryService =authQueryService;
        this.s3FileService = s3FileService;
        this.promotionModifyDTO = promotionModifyDTO;
    }

    @Operation(summary = "프로모션 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = PromotionResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<PromotionResponseMessage> postNotice(@RequestPart("dto") PromotionRegistDTO promotionRegistDTO, // JSON 데이터
                                                               @RequestPart("file") MultipartFile file,
                                                               Principal principal){
        String memberId = authQueryService.selectMemberIdByLoginId(principal.getName());
        promotionRegistDTO.setMemberId(memberId);
        if (file != null && !file.isEmpty()) {
            promotionRegistDTO.setFileUrl(s3FileService.uploadOneFile(file));
            System.out.println("response:1");
        }else if(file==null || file.isEmpty()){
            System.out.println("response:2");
            promotionRegistDTO.setFileUrl(null);
        } else {
            System.out.println("response:3");
            promotionRegistDTO.setFileUrl(null);
        }
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
    public ResponseEntity<PromotionResponseMessage> modifyNotice(
                                                                Principal principal,
                                                                @PathVariable String promotionId,
                                                                @RequestBody PromotionModifyDTO promotionModifyRequestDTO,
                                                                @RequestPart(value = "file", required = false)  MultipartFile file){
        System.out.println("==============================");
        String memberLoginId = principal.getName();
        promotionModifyDTO.setMemberId(memberLoginId);
        System.out.println("==============================");
        promotionModifyDTO.setContent(promotionModifyDTO.getContent());
        if (file != null && !file.isEmpty()) {
            System.out.println("response:1");
            promotionModifyDTO.setFileUrl(s3FileService.uploadOneFile(file));
        }else if(file==null || file.isEmpty()) {
            System.out.println("response:2");
            promotionModifyDTO.setFileUrl(null);
        } else {
            System.out.println("response:3");
            promotionModifyDTO.setFileUrl(s3FileService.uploadOneFile(file));
        }
        promotionCommandService.modifyPromotion(promotionId,promotionModifyRequestDTO,principal);

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
