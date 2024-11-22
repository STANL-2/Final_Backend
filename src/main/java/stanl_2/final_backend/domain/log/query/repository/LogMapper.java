package stanl_2.final_backend.domain.log.query.repository;

import org.apache.ibatis.annotations.Mapper;
import stanl_2.final_backend.domain.log.query.dto.LogDTO;

import java.util.List;

@Mapper
public interface LogMapper {
    List<LogDTO> findLog();
}
