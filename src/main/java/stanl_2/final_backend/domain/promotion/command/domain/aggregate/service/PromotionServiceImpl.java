package stanl_2.final_backend.domain.promotion.command.domain.aggregate.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.domain.notices.common.exception.NoticeCommonException;
import stanl_2.final_backend.domain.notices.common.exception.NoticeErrorCode;
import stanl_2.final_backend.domain.problem.common.exception.ProblemCommonException;
import stanl_2.final_backend.domain.problem.common.exception.ProblemErrorCode;
import stanl_2.final_backend.domain.promotion.command.application.dto.PromotionModifyDTO;
import stanl_2.final_backend.domain.promotion.command.application.dto.PromotionRegistDTO;
import stanl_2.final_backend.domain.promotion.command.application.service.PromotionCommandService;
import stanl_2.final_backend.domain.promotion.command.domain.aggregate.entity.Promotion;
import stanl_2.final_backend.domain.promotion.command.domain.aggregate.repository.PromotionRepository;
import stanl_2.final_backend.domain.promotion.common.exception.PromotionCommonException;
import stanl_2.final_backend.domain.promotion.common.exception.PromotionErrorCode;
import stanl_2.final_backend.global.redis.RedisService;

import java.security.GeneralSecurityException;
import java.security.Principal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service("commandPromotionService")
public class PromotionServiceImpl implements PromotionCommandService {

    private final RedisService redisService;
    private final PromotionRepository promotionRepository;

    private final AuthQueryService authQueryService;
    private final ModelMapper modelMapper;
    private final MemberQueryService memberQueryService;

    @Autowired
    public PromotionServiceImpl(PromotionRepository promotionRepository, ModelMapper modelMapper,RedisService redisService, AuthQueryService authQueryService, MemberQueryService memberQueryService) {
        this.redisService = redisService;
        this.promotionRepository = promotionRepository;
        this.authQueryService = authQueryService;
        this.modelMapper = modelMapper;
        this.memberQueryService =memberQueryService;
    }
    private String getCurrentTimestamp() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Transactional
    @Override
    public void registerPromotion(PromotionRegistDTO promotionRegistDTO, Principal principal) throws GeneralSecurityException {
        redisService.clearPromotionCache();
        String memberId = authQueryService.selectMemberIdByLoginId(promotionRegistDTO.getMemberLoginId());
        memberId=memberQueryService.selectNameById(memberId);
        promotionRegistDTO.setMemberId(memberId);
        try {
            Promotion promotion =modelMapper.map(promotionRegistDTO,Promotion.class);
            promotionRepository.save(promotion);
        } catch (DataIntegrityViolationException e){
            throw new PromotionCommonException(PromotionErrorCode.DATA_INTEGRITY_VIOLATION);
        }catch (Exception e) {
            // 서버 오류
            throw new NoticeCommonException(NoticeErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public PromotionModifyDTO modifyPromotion(String promotionId, PromotionModifyDTO promotionModifyDTO, Principal principal) throws GeneralSecurityException {
        redisService.clearPromotionCache();
        String memberId=authQueryService.selectMemberIdByLoginId(promotionModifyDTO.getMemberLoginId());
        memberId=memberQueryService.selectNameById(memberId);

        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new PromotionCommonException(PromotionErrorCode.PROMOTION_NOT_FOUND));
        if(!promotion.getMemberId().equals(memberId)){
            throw new ProblemCommonException(ProblemErrorCode.AUTHORIZATION_VIOLATION);
        }
        try {
            Promotion updatePromotion = modelMapper.map(promotionModifyDTO, Promotion.class);
            updatePromotion.setPromotionId(promotion.getPromotionId());
            updatePromotion.setMemberId(promotion.getMemberId());
            updatePromotion.setCreatedAt(promotion.getCreatedAt());
            updatePromotion.setActive(promotion.getActive());

            promotionRepository.save(updatePromotion);

            PromotionModifyDTO promotionModify = modelMapper.map(updatePromotion,PromotionModifyDTO.class);

            return promotionModify;
        } catch (DataIntegrityViolationException e) {
            // 데이터 무결성 위반 예외 처리
            throw new PromotionCommonException(PromotionErrorCode.DATA_INTEGRITY_VIOLATION);
        } catch (Exception e) {
            // 서버 오류
            throw new PromotionCommonException(PromotionErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public void deletePromotion(String promotionId, Principal principal) throws GeneralSecurityException {
        redisService.clearPromotionCache();
        String loginId= principal.getName();
        String memberId = authQueryService.selectMemberIdByLoginId(loginId);
        memberId=memberQueryService.selectNameById(memberId);
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(()-> new PromotionCommonException(PromotionErrorCode.PROMOTION_NOT_FOUND));

        if(!promotion.getMemberId().equals(memberId)){
            // 권한 오류
            throw new PromotionCommonException(PromotionErrorCode.AUTHORIZATION_VIOLATION);
        }
        else {
            promotion.setActive(false);
            promotion.setDeletedAt(getCurrentTimestamp());
        }
        try {
            promotionRepository.save(promotion);
        } catch (DataIntegrityViolationException e) {
            // 데이터 무결성 위반 예외 처리
            throw new PromotionCommonException(PromotionErrorCode.DATA_INTEGRITY_VIOLATION);
        } catch (Exception e) {
            // 서버 오류
            throw new PromotionCommonException(PromotionErrorCode.INTERNAL_SERVER_ERROR);
        }

    }
}
