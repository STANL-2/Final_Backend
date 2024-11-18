package stanl_2.final_backend.domain.education.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stanl_2.final_backend.domain.education.query.dto.EducationDTO;

import java.util.List;

@Mapper
public interface EducationMapper {
    List<EducationDTO> selectEducationInfo(@Param("memberId") String memberId);
}
