package stanl_2.final_backend.domain.evaluation.query.service;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stanl_2.final_backend.domain.evaluation.query.dto.EvaluationDTO;
import stanl_2.final_backend.domain.evaluation.query.dto.EvaluationSearchDTO;

import java.security.GeneralSecurityException;
import java.util.Map;

public interface EvaluationQueryService {

    EvaluationDTO selectEvaluationById(String id);

    Page<EvaluationDTO> selectAllEvaluationsByManager(EvaluationDTO evaluationDTO, Pageable pageable) throws GeneralSecurityException;
    Page<EvaluationDTO> selectAllEvaluationsByRepresentative(EvaluationDTO evaluationDTO, Pageable pageable) throws GeneralSecurityException;

    Page<EvaluationDTO> selectEvaluationBySearchByManager(Pageable pageable, EvaluationSearchDTO evaluationSearchDTO) throws GeneralSecurityException;
    Page<EvaluationDTO> selectEvaluationBySearchByRepresentative(Pageable pageable, EvaluationSearchDTO evaluationSearchDTO) throws GeneralSecurityException;

    void exportEvaluationToExcel(HttpServletResponse response);
}
