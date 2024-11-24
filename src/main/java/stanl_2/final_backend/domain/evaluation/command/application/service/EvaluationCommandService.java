package stanl_2.final_backend.domain.evaluation.command.application.service;

import stanl_2.final_backend.domain.A_sample.command.application.dto.SampleModifyDTO;
import stanl_2.final_backend.domain.evaluation.command.application.dto.EvaluationModifyDTO;
import stanl_2.final_backend.domain.evaluation.command.application.dto.EvaluationRegistDTO;

public interface EvaluationCommandService {
    void registerEvaluation(EvaluationRegistDTO evaluationRegistRequestDTO);

    void modifyEvaluation(EvaluationModifyDTO evaluationModifyRequestDTO);

    void deleteEvaluation(String id);
}
