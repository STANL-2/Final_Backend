package stanl_2.final_backend.domain.evaluation.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stanl_2.final_backend.domain.evaluation.query.dto.EvaluationDTO;
import stanl_2.final_backend.domain.evaluation.query.dto.EvaluationSearchDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface EvaluationMapper {

    List<EvaluationDTO> findEvaluationByCenterId(@Param("size") int size
                                                        ,@Param("offset") int offset
                                                        ,@Param("centerId") String centerId);

    EvaluationDTO findEvaluationById(String id);

    List<EvaluationDTO> findAllEvaluations(@Param("size") int size
            ,@Param("offset") int offset);

    int findEvaluationCount();

    int findEvaluationCountByCenterId(@Param("centerId") String centerId);

    List<EvaluationDTO> findEvaluationBySearch(@Param("size") int size
            ,@Param("offset") int offset
            ,@Param("evaluationSearchDTO") EvaluationSearchDTO evaluationSearchDTO);

    List<EvaluationDTO> findEvaluationByCenterIdAndSearch(@Param("size") int size
            ,@Param("offset") int offset
            ,@Param("evaluationSearchDTO") EvaluationSearchDTO evaluationSearchDTO
            ,@Param("centerId") String centerId);

    int findEvaluationBySearchCount(@Param("evaluationSearchDTO") EvaluationSearchDTO evaluationSearchDTO);

    int findEvaluationByCenterIdAndSearchCount(@Param("evaluationSearchDTO") EvaluationSearchDTO evaluationSearchDTO);
}
