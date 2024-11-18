package stanl_2.final_backend.domain.evaluation.query.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stanl_2.final_backend.domain.evaluation.query.dto.EvaluationDTO;

import java.util.Map;

public interface EvaluationQueryService {
    Page<Map<String, Object>> selectAllEvaluations(Pageable pageable);

    EvaluationDTO selectEvaluationById(String id);

    Page<Map<String, Object>> selectEvaluationByCenter(String centerId, Pageable pageable);
}