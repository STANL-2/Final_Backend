package stanl_2.final_backend.domain.log.query.service;

import stanl_2.final_backend.domain.log.query.dto.LogDTO;

import java.util.List;

public interface LogQueryService {
    List<LogDTO> selectLog();
}
