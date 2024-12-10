package stanl_2.final_backend.domain.promotion.command.domain.aggregate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.domain.promotion.command.application.dto.PromotionRegistDTO;
import stanl_2.final_backend.domain.promotion.command.domain.aggregate.entity.Promotion;
import stanl_2.final_backend.domain.promotion.command.domain.aggregate.repository.PromotionRepository;
import stanl_2.final_backend.global.redis.RedisService;

import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PromotionServiceTests {

    @InjectMocks
    private PromotionServiceImpl promotionService;

    @Mock
    private PromotionRepository promotionRepository;

    @Mock
    private AuthQueryService authQueryService;

    @Mock
    private MemberQueryService memberQueryService;

    @Mock
    private RedisService redisService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private Principal principal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 테스트 1: registerPromotion 성공 케이스
    @Test
    void 프로모션_등록() throws Exception {
        PromotionRegistDTO registDTO = new PromotionRegistDTO();
        registDTO.setMemberLoginId("loginId");
        registDTO.setContent("promotion content");

        when(authQueryService.selectMemberIdByLoginId("loginId")).thenReturn("memberId");
        when(memberQueryService.selectNameById("memberId")).thenReturn("memberName");

        Promotion promotion = new Promotion();
        when(modelMapper.map(registDTO, Promotion.class)).thenReturn(promotion);
        when(promotionRepository.save(promotion)).thenReturn(promotion);

        promotionService.registerPromotion(registDTO, principal);

        verify(redisService).clearPromotionCache();
        verify(authQueryService).selectMemberIdByLoginId("loginId");
        verify(memberQueryService).selectNameById("memberId");
        verify(promotionRepository).save(promotion);
    }

    @Test
    void 프로모션_삭제() throws GeneralSecurityException {
        // Arrange
        String promotionId = "test";
        String loginId = "loginId";
        String memberId = "memberId";

        Promotion promotion = new Promotion();
        promotion.setMemberId(memberId);

        when(principal.getName()).thenReturn(loginId);
        when(authQueryService.selectMemberIdByLoginId(loginId)).thenReturn(memberId);
        when(memberQueryService.selectNameById(memberId)).thenReturn(memberId);
        when(promotionRepository.findById(promotionId)).thenReturn(Optional.of(promotion));

        promotionService.deletePromotion(promotionId, principal);

        assertNotNull(promotion.getDeletedAt());
    }
}