package stanl_2.final_backend.domain.log.query.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stanl_2.final_backend.domain.log.query.dto.LogDTO;
import stanl_2.final_backend.domain.log.query.dto.LogSearchDTO;

import java.util.List;

public interface LogQueryService {
    Page<LogDTO> selectLogs(Pageable pageable, LogSearchDTO searchLogDTO);
}
