package stanl_2.final_backend.domain.problem.command.application.service;

import stanl_2.final_backend.domain.problem.command.application.dto.ProblemModifyDTO;
import stanl_2.final_backend.domain.problem.command.application.dto.ProblemRegistDTO;

import java.security.GeneralSecurityException;
import java.security.Principal;

public interface ProblemCommandService {

    void registerProblem(ProblemRegistDTO problemRegistDTO, Principal principal) throws GeneralSecurityException;

    ProblemModifyDTO modifyProblem(String problemId, ProblemModifyDTO problemModifyDTO, Principal principal) throws GeneralSecurityException;

    void modifyStatus(String problemId, Principal principal);
    void deleteProblem(String problemId, Principal principal);
}
