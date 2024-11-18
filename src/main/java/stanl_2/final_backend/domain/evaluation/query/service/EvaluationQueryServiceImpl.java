package stanl_2.final_backend.domain.evaluation.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.evaluation.common.exception.EvaluationCommonException;
import stanl_2.final_backend.domain.evaluation.common.exception.EvaluationErrorCode;
import stanl_2.final_backend.domain.evaluation.common.util.EvaluationRequestList;
import stanl_2.final_backend.domain.evaluation.query.dto.EvaluationDTO;
import stanl_2.final_backend.domain.evaluation.query.respository.EvaluationMapper;

import java.util.List;
import java.util.Map;

@Service
public class EvaluationQueryServiceImpl implements EvaluationQueryService {

    private final EvaluationMapper evaluationMapper;

    @Autowired
    public EvaluationQueryServiceImpl(EvaluationMapper evaluationMapper) {
        this.evaluationMapper = evaluationMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Map<String, Object>> selectAllEvaluations(Pageable pageable) {

        EvaluationRequestList<?> requestList = EvaluationRequestList.builder()
                .pageable(pageable)
                .build();

        List<Map<String, Object>> evaluationList = evaluationMapper.findAllEvaluations(requestList);

        int total = evaluationMapper.findEvaluationCount();

        if(evaluationList == null || total == 0){
            throw new EvaluationCommonException(EvaluationErrorCode.EVALUATION_NOT_FOUND);
        }
        return new PageImpl<>(evaluationList, pageable, total);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<Map<String, Object>> selectEvaluationByCenter(String centerId, Pageable pageable) {
        EvaluationRequestList<?> requestList = EvaluationRequestList.builder()
                .pageable(pageable)
                .build();

        List<Map<String, Object>> evaluationList = evaluationMapper.findEvaluationByCenterId(centerId, requestList);

        int total = evaluationMapper.findEvaluationCountByCenterId();

        if(evaluationList == null || total == 0){
            throw new EvaluationCommonException(EvaluationErrorCode.EVALUATION_NOT_FOUND);
        }
        return new PageImpl<>(evaluationList, pageable, total);
    }

    @Override
    @Transactional(readOnly = true)
    public EvaluationDTO selectEvaluationById(String id) {

        EvaluationDTO evaluationDTO = evaluationMapper.findEvaluationById(id);

        if(evaluationDTO == null){
            throw new EvaluationCommonException(EvaluationErrorCode.EVALUATION_NOT_FOUND);
        }
        return evaluationDTO;

    }


}
