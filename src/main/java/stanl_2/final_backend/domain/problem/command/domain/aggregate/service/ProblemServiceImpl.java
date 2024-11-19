package stanl_2.final_backend.domain.problem.command.domain.aggregate.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.problem.command.application.dto.ProblemModifyDTO;
import stanl_2.final_backend.domain.problem.command.application.dto.ProblemRegistDTO;
import stanl_2.final_backend.domain.problem.command.application.service.ProblemCommandService;
import stanl_2.final_backend.domain.problem.command.domain.aggregate.entity.Problem;
import stanl_2.final_backend.domain.problem.command.domain.aggregate.repository.ProblemRepository;
import stanl_2.final_backend.domain.problem.common.exception.ProblemCommonException;
import stanl_2.final_backend.domain.problem.common.exception.ProblemErrorCode;
import stanl_2.final_backend.domain.promotion.common.exception.PromotionCommonException;
import stanl_2.final_backend.domain.promotion.common.exception.PromotionErrorCode;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service("commandProblemService")
public class ProblemServiceImpl implements ProblemCommandService {

    private final ProblemRepository problemRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public ProblemServiceImpl(ProblemRepository problemRepository, ModelMapper modelMapper) {
        this.problemRepository = problemRepository;
        this.modelMapper = modelMapper;
    }
    private String getCurrentTimestamp() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Transactional
    @Override
    public void registerProblem(ProblemRegistDTO problemRegistDTO) {
        Problem problem =modelMapper.map(problemRegistDTO,Problem.class);
        problemRepository.save(problem);
    }

    @Transactional
    @Override
    public ProblemModifyDTO modifyProblem(String problemId, ProblemModifyDTO problemModifyDTO) {
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new ProblemCommonException(ProblemErrorCode.PROBLEM_NOT_FOUND));

        Problem updateProblem = modelMapper.map(problemModifyDTO, Problem.class);
        updateProblem.setProblemId(problem.getProblemId());
        updateProblem.setMemberId(problem.getMemberId());
        updateProblem.setCreatedAt(problem.getCreatedAt());
        updateProblem.setActive(problem.getActive());
        updateProblem.setCustomerId(problem.getCustomerId());
        updateProblem.setProductId(problem.getProductId());

        problemRepository.save(updateProblem);

        ProblemModifyDTO problemModify = modelMapper.map(updateProblem,ProblemModifyDTO.class);

        return problemModify;
    }

    @Transactional
    @Override
    public void deleteProblem(String problemId) {
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(()-> new PromotionCommonException(PromotionErrorCode.PROMOTION_NOT_FOUND));

        problem.setActive(false);
        problem.setDeletedAt(getCurrentTimestamp());

        problemRepository.save(problem);
    }
}
