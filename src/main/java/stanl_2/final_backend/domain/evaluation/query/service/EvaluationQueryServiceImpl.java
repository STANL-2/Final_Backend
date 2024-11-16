package stanl_2.final_backend.domain.evaluation.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.evaluation.common.exception.EvaluationCommonException;
import stanl_2.final_backend.domain.evaluation.common.exception.EvaluationErrorCode;
import stanl_2.final_backend.domain.evaluation.query.dto.EvaluationDTO;
import stanl_2.final_backend.domain.evaluation.query.respository.EvaluationMapper;

@Service
public class EvaluationQueryServiceImpl implements EvaluationQueryService {

    private final EvaluationMapper evaluationMapper;

    @Autowired
    public EvaluationQueryServiceImpl(EvaluationMapper evaluationMapper) {
        this.evaluationMapper = evaluationMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public String selectEvaluationName(String id) {

        String name = evaluationMapper.selectNameById(id);

        if(name == null){
            throw new EvaluationCommonException(EvaluationErrorCode.SAMPLE_NOT_FOUND);
        }

        return name;
    }

    @Override
    @Transactional(readOnly = true)
    public EvaluationDTO selectEvaluation(String id) {

        EvaluationDTO evaluationDTO = evaluationMapper.selectById(id);

        if(evaluationDTO == null){
            throw new EvaluationCommonException(EvaluationErrorCode.SAMPLE_NOT_FOUND);
        }

        return evaluationDTO;
    }


}
