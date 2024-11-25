package stanl_2.final_backend.domain.evaluation.command.application.service;

import org.springframework.web.multipart.MultipartFile;
import stanl_2.final_backend.domain.A_sample.command.application.dto.SampleModifyDTO;
import stanl_2.final_backend.domain.evaluation.command.application.dto.EvaluationModifyDTO;
import stanl_2.final_backend.domain.evaluation.command.application.dto.EvaluationRegistDTO;

public interface EvaluationCommandService {
    void registerEvaluation(EvaluationRegistDTO evaluationRegistRequestDTO, MultipartFile fileUrl);

    void modifyEvaluation(EvaluationModifyDTO evaluationModifyRequestDTO, MultipartFile fileUrl);

    void deleteEvaluation(String id);
}
