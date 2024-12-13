package stanl_2.final_backend.domain.problem.query.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stanl_2.final_backend.domain.problem.query.dto.ProblemDTO;
import stanl_2.final_backend.domain.problem.query.dto.ProblemSearchDTO;

public interface ProblemService {
    Page<ProblemDTO> findProblems(Pageable pageable, ProblemSearchDTO problemSearchDTO);
    ProblemDTO findProblem(String problemId);
    void exportProblemsToExcel(HttpServletResponse response);

}
