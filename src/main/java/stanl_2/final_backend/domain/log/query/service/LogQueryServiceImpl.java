package stanl_2.final_backend.domain.log.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.log.query.dto.LogDTO;
import stanl_2.final_backend.domain.log.query.dto.LogSearchDTO;
import stanl_2.final_backend.domain.log.query.repository.LogMapper;

import java.util.List;

@Slf4j
@Service(value = "queryLogService")
public class LogQueryServiceImpl implements LogQueryService {

    private final LogMapper logMapper;

    @Autowired
    public LogQueryServiceImpl(LogMapper logMapper) {
        this.logMapper = logMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LogDTO> selectLogs(Pageable pageable, LogSearchDTO searchLogDTO) {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        List<LogDTO> logs = logMapper.findLogs(offset, size, searchLogDTO);

        log.info("####");
        log.info("{}", logs);
        int totalElements = logMapper.findLogsCnt();
        return new PageImpl<>(logs, pageable, totalElements);
    }
}
