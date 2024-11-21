package stanl_2.final_backend.domain.problem.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import stanl_2.final_backend.domain.problem.query.dto.ProblemDTO;
import stanl_2.final_backend.domain.problem.query.dto.ProblemSearchDTO;

import java.util.List;

@Mapper
public interface ProblemMapper {
    List<ProblemDTO> findProblems(
            @Param("offset") Integer offset,
            @Param("size") Integer size,
            @Param("problemDTO") ProblemSearchDTO problemSearchDTO
    );
    Integer findProblemsCount(@Param("problemSearchDTO") ProblemSearchDTO problemSearchDTO);

    ProblemDTO findProblem(@Param("problemId") String problemId);
}
