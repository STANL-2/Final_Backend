package stanl_2.final_backend.domain.evaluation.query.service;


import stanl_2.final_backend.domain.evaluation.query.dto.EvaluationDTO;

public interface EvaluationQueryService {
    EvaluationDTO selectEvaluation(String id);

    String selectEvaluationName(String id);
}
