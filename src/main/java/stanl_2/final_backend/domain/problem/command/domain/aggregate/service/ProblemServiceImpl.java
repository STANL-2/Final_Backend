package stanl_2.final_backend.domain.problem.command.domain.aggregate.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.domain.notices.common.exception.NoticeCommonException;
import stanl_2.final_backend.domain.notices.common.exception.NoticeErrorCode;
import stanl_2.final_backend.domain.problem.command.application.dto.ProblemModifyDTO;
import stanl_2.final_backend.domain.problem.command.application.dto.ProblemRegistDTO;
import stanl_2.final_backend.domain.problem.command.application.service.ProblemCommandService;
import stanl_2.final_backend.domain.problem.command.domain.aggregate.entity.Problem;
import stanl_2.final_backend.domain.problem.command.domain.aggregate.repository.ProblemRepository;
import stanl_2.final_backend.domain.problem.common.exception.ProblemCommonException;
import stanl_2.final_backend.domain.problem.common.exception.ProblemErrorCode;
import stanl_2.final_backend.global.redis.RedisService;

import java.security.GeneralSecurityException;
import java.security.Principal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service("commandProblemService")
public class ProblemServiceImpl implements ProblemCommandService {

    private final ProblemRepository problemRepository;
    private final AuthQueryService authQueryService;
    private final RedisService redisService;
    private final ModelMapper modelMapper;
    private final MemberQueryService memberQueryService;


    @Autowired
    public ProblemServiceImpl(ProblemRepository problemRepository,
                              AuthQueryService authQueryService,
                              ModelMapper modelMapper,
                              RedisService redisService,
                              MemberQueryService memberQueryService) {
        this.problemRepository = problemRepository;
        this.modelMapper = modelMapper;
        this.redisService = redisService;
        this.authQueryService =authQueryService;
        this.memberQueryService = memberQueryService;
    }
    private String getCurrentTimestamp() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Transactional
    @Override
    public void registerProblem(ProblemRegistDTO problemRegistDTO,
                                Principal principal) throws GeneralSecurityException {
        redisService.clearProblemCache();
        String memberId = authQueryService.selectMemberIdByLoginId(problemRegistDTO.getMemberLoginId());
        memberId=memberQueryService.selectNameById(memberId);
        problemRegistDTO.setMemberId(memberId);
        try {
            Problem problem = modelMapper.map(problemRegistDTO, Problem.class);
            problemRepository.save(problem);
        } catch (DataIntegrityViolationException e){
            throw new ProblemCommonException(ProblemErrorCode.DATA_INTEGRITY_VIOLATION);
        }catch (Exception e) {
            // 서버 오류
            throw new NoticeCommonException(NoticeErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public ProblemModifyDTO modifyProblem(String problemId, ProblemModifyDTO problemModifyDTO,Principal principal) throws GeneralSecurityException {
        redisService.clearProblemCache();
        String memberId = authQueryService.selectMemberIdByLoginId(problemModifyDTO.getMemberLoginId());
        memberId=memberQueryService.selectNameById(memberId);

        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new ProblemCommonException(ProblemErrorCode.PROBLEM_NOT_FOUND));
        if(!problem.getMemberId().equals(memberId)){
            throw new ProblemCommonException(ProblemErrorCode.AUTHORIZATION_VIOLATION);

        }
        try {
            System.out.println("test1");
            Problem updateProblem = modelMapper.map(problemModifyDTO, Problem.class);
            updateProblem.setProblemId(problem.getProblemId());
            updateProblem.setMemberId(problem.getMemberId());
            updateProblem.setStatus(problem.getStatus());
            updateProblem.setCreatedAt(problem.getCreatedAt());
            updateProblem.setActive(problem.getActive());
            updateProblem.setCustomerId(problem.getCustomerId());
            updateProblem.setProductId(problem.getProductId());
            problemRepository.save(updateProblem);

            ProblemModifyDTO problemModify = modelMapper.map(updateProblem,ProblemModifyDTO.class);

            return problemModify;
        } catch (DataIntegrityViolationException e) {
            // 데이터 무결성 위반 예외 처리
            throw new ProblemCommonException(ProblemErrorCode.DATA_INTEGRITY_VIOLATION);
        } catch (Exception e) {
            // 서버 오류
            throw new ProblemCommonException(ProblemErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional
    @Override
    public void modifyStatus(String problemId,Principal principal) {
        redisService.clearProblemCache();
        String memberId= principal.getName();
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new ProblemCommonException(ProblemErrorCode.PROBLEM_NOT_FOUND));
        if(problem.getMemberId().equals(memberId)){
            throw new ProblemCommonException(ProblemErrorCode.AUTHORIZATION_VIOLATION);
        }
        try {
            problem.setStatus("DONE");
            problem.setUpdatedAt(getCurrentTimestamp());
        } catch (DataIntegrityViolationException e) {
            // 데이터 무결성 위반 예외 처리
            throw new ProblemCommonException(ProblemErrorCode.DATA_INTEGRITY_VIOLATION);
        } catch (Exception e) {
            // 서버 오류
            throw new ProblemCommonException(ProblemErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @Override
    public void deleteProblem(String problemId, Principal principal) {
        redisService.clearProblemCache();
        String memberId= principal.getName();
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(()-> new ProblemCommonException(ProblemErrorCode.PROBLEM_NOT_FOUND));

        if(!problem.getMemberId().equals(memberId)){
            // 권한 오류
            throw new ProblemCommonException(ProblemErrorCode.AUTHORIZATION_VIOLATION);
        }

        problem.setActive(false);
        problem.setDeletedAt(getCurrentTimestamp());

        try {
            problemRepository.save(problem);
        } catch (DataIntegrityViolationException e) {
            // 데이터 무결성 위반 예외 처리
            throw new ProblemCommonException(ProblemErrorCode.DATA_INTEGRITY_VIOLATION);
        } catch (Exception e) {
            // 서버 오류
            throw new ProblemCommonException(ProblemErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
