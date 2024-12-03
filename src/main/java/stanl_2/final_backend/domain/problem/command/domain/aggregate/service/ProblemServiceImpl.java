package stanl_2.final_backend.domain.problem.command.domain.aggregate.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.notices.common.exception.NoticeCommonException;
import stanl_2.final_backend.domain.notices.common.exception.NoticeErrorCode;
import stanl_2.final_backend.domain.problem.command.application.dto.ProblemModifyDTO;
import stanl_2.final_backend.domain.problem.command.application.dto.ProblemRegistDTO;
import stanl_2.final_backend.domain.problem.command.application.service.ProblemCommandService;
import stanl_2.final_backend.domain.problem.command.domain.aggregate.entity.Problem;
import stanl_2.final_backend.domain.problem.command.domain.aggregate.repository.ProblemRepository;
import stanl_2.final_backend.domain.problem.common.exception.ProblemCommonException;
import stanl_2.final_backend.domain.problem.common.exception.ProblemErrorCode;
import stanl_2.final_backend.domain.promotion.common.exception.PromotionCommonException;
import stanl_2.final_backend.domain.promotion.common.exception.PromotionErrorCode;
import stanl_2.final_backend.domain.schedule.common.exception.ScheduleCommonException;
import stanl_2.final_backend.domain.schedule.common.exception.ScheduleErrorCode;
import stanl_2.final_backend.global.redis.RedisService;

import java.security.Principal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service("commandProblemService")
public class ProblemServiceImpl implements ProblemCommandService {

    private final ProblemRepository problemRepository;
    private final RedisService redisService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProblemServiceImpl(ProblemRepository problemRepository, ModelMapper modelMapper, RedisService redisService) {
        this.problemRepository = problemRepository;
        this.modelMapper = modelMapper;
        this.redisService = redisService;
    }
    private String getCurrentTimestamp() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Transactional
    @Override
    public void registerProblem(ProblemRegistDTO problemRegistDTO, Principal principal) {
        redisService.clearProblemCache();
        String memberId =principal.getName();
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
    public ProblemModifyDTO modifyProblem(String problemId, ProblemModifyDTO problemModifyDTO,Principal principal) {
        redisService.clearProblemCache();
        String memberId= principal.getName();
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new ProblemCommonException(ProblemErrorCode.PROBLEM_NOT_FOUND));
        if(!problem.getMemberId().equals(memberId)){
            throw new ProblemCommonException(ProblemErrorCode.AUTHORIZATION_VIOLATION);
        }
        try {
            Problem updateProblem = modelMapper.map(problemModifyDTO, Problem.class);
            updateProblem.setProblemId(problem.getProblemId());
            updateProblem.setProblemId(problem.getTitle());
            updateProblem.setProblemId(problem.getContent());
            updateProblem.setMemberId(problem.getMemberId());
            updateProblem.setStatus("DONE");
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
        if(!problem.getMemberId().equals(memberId)){
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
