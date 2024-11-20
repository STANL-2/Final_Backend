package stanl_2.final_backend.domain.problem.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.problem.query.dto.ProblemDTO;
import stanl_2.final_backend.domain.problem.query.dto.ProblemSearchDTO;
import stanl_2.final_backend.domain.problem.query.repository.ProblemMapper;
import stanl_2.final_backend.domain.promotion.query.dto.PromotionDTO;

import java.util.List;

@Service("queryProblemServiceImpl")
public class ProblemServiceImpl implements ProblemService {
    private final ProblemMapper problemMapper;
    @Autowired
    public ProblemServiceImpl(ProblemMapper problemMapper) {
        this.problemMapper = problemMapper;
    }

    @Override
    public Page<ProblemDTO> findProblems(Pageable pageable, ProblemSearchDTO problemSearchDTO) {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();
        List<ProblemDTO> problems = problemMapper.findProblems(offset,size,problemSearchDTO);
        Integer count = problemMapper.findProblemsCount(problemSearchDTO);
        int totalCount = (count != null) ?  problemMapper.findProblemsCount(problemSearchDTO) : 0;

        return new PageImpl<>(problems, pageable, totalCount);
    }

    @Override
    public ProblemDTO findProblem(String problemId) {
        ProblemDTO problemDTO = problemMapper.findProblem(problemId);
        return problemDTO;
    }
}
