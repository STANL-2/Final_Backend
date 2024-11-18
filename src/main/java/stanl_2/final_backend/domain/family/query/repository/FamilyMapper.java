package stanl_2.final_backend.domain.family.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stanl_2.final_backend.domain.family.query.dto.FamilyDTO;

import java.util.List;

@Mapper
public interface FamilyMapper {
    List<FamilyDTO> selectFamilyInfo(@Param("memberId") String memberId);
}
