package stanl_2.final_backend.domain.problem.command.domain.aggregate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.domain.problem.command.application.dto.ProblemRegistDTO;
import stanl_2.final_backend.domain.problem.command.domain.aggregate.entity.Problem;
import stanl_2.final_backend.domain.problem.command.domain.aggregate.repository.ProblemRepository;
import stanl_2.final_backend.global.redis.RedisService;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProblemServiceTests {

    @InjectMocks
    private ProblemServiceImpl problemService;
    @Mock
    private ProblemRepository problemRepository;

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

    @Test
    void 문제사항_등록() throws Exception {
        ProblemRegistDTO registDTO = new ProblemRegistDTO();
        registDTO.setMemberLoginId("loginId");
        registDTO.setContent("contentTest");
        when(authQueryService.selectMemberIdByLoginId("loginId")).thenReturn("memberId");
        when(memberQueryService.selectNameById("memberId")).thenReturn("memberName");
        Problem problem = new Problem();
        when(modelMapper.map(registDTO, Problem.class)).thenReturn(problem);
        when(problemRepository.save(problem)).thenReturn(problem);


        problemService.registerProblem(registDTO, principal);


        verify(redisService).clearProblemCache();
        verify(authQueryService).selectMemberIdByLoginId("loginId");
        verify(memberQueryService).selectNameById("memberId");
        verify(problemRepository).save(problem);
    }


    @Test
    void 문제사항_상태수정() {
        Problem problem = new Problem();
        problem.setMemberId("memberId");
        when(problemRepository.findById("problemId")).thenReturn(Optional.of(problem));
        when(principal.getName()).thenReturn("memberId1");

        problemService.modifyStatus("problemId", principal);

        assertEquals("DONE", problem.getStatus());
    }

    @Test
    void 문제사항_삭제() {
        Problem problem = new Problem();
        problem.setMemberId("memberId");
        when(problemRepository.findById("problemId")).thenReturn(Optional.of(problem));
        when(principal.getName()).thenReturn("memberId");

        problemService.deleteProblem("problemId", principal);

        assertNotNull(problem.getDeletedAt());
    }
}
