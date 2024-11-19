package stanl_2.final_backend.domain.career.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stanl_2.final_backend.domain.career.query.dto.CareerDTO;

import java.util.List;

@Mapper
public interface CareerMapper {
    List<CareerDTO> selectCareerInfo(@Param("memberId") String memberId);
}
