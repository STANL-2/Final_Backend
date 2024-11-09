package stanl_2.final_backend.domain.center.query.repository;

import org.apache.ibatis.annotations.Mapper;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectIdDTO;

@Mapper
public interface CenterMapper {
    CenterSelectIdDTO findCenterById(Long centerId);
}
