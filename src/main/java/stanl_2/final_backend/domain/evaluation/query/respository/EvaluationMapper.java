package stanl_2.final_backend.domain.evaluation.query.respository;

import org.apache.ibatis.annotations.Mapper;
import stanl_2.final_backend.domain.evaluation.query.dto.EvaluationDTO;

@Mapper
public interface EvaluationMapper {

    String selectNameById(String id);

    EvaluationDTO selectById(String id);
}
