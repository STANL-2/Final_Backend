package stanl_2.final_backend.domain.evaluation.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stanl_2.final_backend.domain.evaluation.common.util.EvaluationRequestList;
import stanl_2.final_backend.domain.evaluation.query.dto.EvaluationDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface EvaluationMapper {

    List<EvaluationDTO> findEvaluationByCenterId(@Param("size") int size
                                                        ,@Param("offset") int offset
                                                        ,@Param("centerId") String centerId);

    EvaluationDTO findEvaluationById(String id);

    List<Map<String, Object>> findAllEvaluations(EvaluationRequestList<?> requestList);

    int findEvaluationCount();

    int findEvaluationCountByCenterId(@Param("centerId") String centerId);
}
