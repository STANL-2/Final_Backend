package stanl_2.final_backend.domain.problem.command.application.service;

import stanl_2.final_backend.domain.problem.command.application.dto.ProblemModifyDTO;
import stanl_2.final_backend.domain.problem.command.application.dto.ProblemRegistDTO;

import java.security.Principal;

public interface ProblemCommandService {

    void registerProblem(ProblemRegistDTO problemRegistDTO, Principal principal);

    ProblemModifyDTO modifyProblem(String problemId, ProblemModifyDTO problemModifyDTO, Principal principal);

    void deleteProblem(String problemId, Principal principal);
}
