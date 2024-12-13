package stanl_2.final_backend.domain.promotion.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.domain.problem.command.domain.aggregate.entity.Problem;
import stanl_2.final_backend.domain.promotion.command.application.dto.PromotionModifyDTO;
import stanl_2.final_backend.domain.promotion.command.application.dto.PromotionRegistDTO;
import stanl_2.final_backend.domain.promotion.command.application.service.PromotionCommandService;
import stanl_2.final_backend.domain.promotion.command.domain.aggregate.entity.Promotion;
import stanl_2.final_backend.domain.promotion.common.response.PromotionResponseMessage;
import stanl_2.final_backend.domain.s3.command.domain.service.S3FileServiceImpl;

import java.security.GeneralSecurityException;
import java.security.Principal;

@RestController("commandPromotionController")
@RequestMapping("/api/v1/promotion")
public class PromotionController {
    private final PromotionCommandService promotionCommandService;
    private final AuthQueryService authQueryService;
    private final S3FileServiceImpl s3FileService;
    private final MemberQueryService memberQueryService;

    private final ModelMapper modelMapper;

    @Autowired
    public PromotionController(PromotionCommandService promotionCommandService, AuthQueryService authQueryService, S3FileServiceImpl s3FileService,MemberQueryService memberQueryService,ModelMapper modelMapper) {
        this.promotionCommandService = promotionCommandService;
        this.authQueryService =authQueryService;
        this.s3FileService = s3FileService;
        this.memberQueryService =memberQueryService;
        this.modelMapper =modelMapper;
    }

    @Operation(summary = "프로모션 작성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = PromotionResponseMessage.class))})
    })
    @PostMapping("")
    public ResponseEntity<PromotionResponseMessage> postNotice(@RequestPart("dto") PromotionRegistDTO promotionRegistDTO, // JSON 데이터
                                                               @RequestPart(value = "file", required = false)  MultipartFile file,
                                                               Principal principal) throws GeneralSecurityException {
        String memberLoginId = principal.getName();
        promotionRegistDTO.setMemberLoginId(memberLoginId);
        if (file != null && !file.isEmpty()) {
            promotionRegistDTO.setFileUrl(s3FileService.uploadOneFile(file));
        }else if(file==null || file.isEmpty()){
            promotionRegistDTO.setFileUrl(null);
        } else {
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
                                                                @PathVariable String promotionId,
                                                                @RequestPart("dto") PromotionModifyDTO promotionModifyDTO,
                                                                @RequestPart(value = "file", required = false)  MultipartFile file,
                                                                Principal principal) throws GeneralSecurityException {
        String memberLoginId = principal.getName();
        String memberId = authQueryService.selectMemberIdByLoginId(memberLoginId);
        memberId=memberQueryService.selectNameById(memberId);
        promotionModifyDTO.setMemberId(memberId);
        promotionModifyDTO.setMemberLoginId(memberLoginId);
        promotionModifyDTO.setContent(promotionModifyDTO.getContent());
        Promotion updatePromotion = modelMapper.map(promotionModifyDTO, Promotion.class);
        System.out.println("1."+updatePromotion.getFileUrl());
        if(promotionModifyDTO.getFileUrl()==null){
            System.out.println("테스트중");
            promotionModifyDTO.setFileUrl(updatePromotion.getFileUrl());
        }
        if (file != null && !file.isEmpty()) {
            System.out.println("1번");
            promotionModifyDTO.setFileUrl(s3FileService.uploadOneFile(file));
        }else if(file==null || file.isEmpty()) {
            System.out.println("2번");
            promotionModifyDTO.setFileUrl(updatePromotion.getFileUrl());
        } else {
            System.out.println("3번");
            promotionModifyDTO.setFileUrl(s3FileService.uploadOneFile(file));
        }
        promotionCommandService.modifyPromotion(promotionId,promotionModifyDTO,principal);

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
    public ResponseEntity<PromotionResponseMessage> deleteNotice(@PathVariable String promotionId, Principal principal) throws GeneralSecurityException {

        promotionCommandService.deletePromotion(promotionId,principal);

        return ResponseEntity.ok(PromotionResponseMessage.builder()
                .httpStatus(200)
                .msg("성공")
                .result(null)
                .build());
    }
}
