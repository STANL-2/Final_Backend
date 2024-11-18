package stanl_2.final_backend.domain.evaluation.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stanl_2.final_backend.domain.evaluation.common.util.EvaluationRequestList;
import stanl_2.final_backend.domain.evaluation.query.dto.EvaluationDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface EvaluationMapper {

    List<Map<String, Object>> findEvaluationByCenterId(@Param("requestList") EvaluationRequestList<?> requestList);

    EvaluationDTO findEvaluationById(String id);

    List<Map<String, Object>> findAllEvaluations(EvaluationRequestList<?> requestList);

    int findEvaluationCount();

    int findEvaluationCountByCenterId();
}