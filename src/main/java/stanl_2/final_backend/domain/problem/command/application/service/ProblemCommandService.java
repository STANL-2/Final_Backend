package stanl_2.final_backend.domain.problem.command.application.service;

import stanl_2.final_backend.domain.problem.command.application.dto.ProblemModifyDTO;
import stanl_2.final_backend.domain.problem.command.application.dto.ProblemRegistDTO;

public interface ProblemCommandService {

    void registerProblem(ProblemRegistDTO problemRegistDTO);

    ProblemModifyDTO modifyProblem(String problemId, ProblemModifyDTO problemModifyDTO);

    void deleteProblem(String problemId);
}
