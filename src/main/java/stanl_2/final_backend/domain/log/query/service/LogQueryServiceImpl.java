package stanl_2.final_backend.domain.log.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.log.query.dto.LogDTO;
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
    public List<LogDTO> selectLog() {

        List<LogDTO> log = logMapper.findLog();

        return log;
    }
}
