package stanl_2.final_backend.domain.center.query.repository;

import org.apache.ibatis.annotations.Mapper;
import stanl_2.final_backend.domain.center.common.util.RequestList;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectAllDTO;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectIdDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface CenterMapper {
    CenterSelectIdDTO findCenterById(String id);

    List<Map<String, Object>> findCenterAll(RequestList<?> requestList);

    int findCenterCount();
}
