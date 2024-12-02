package stanl_2.final_backend.domain.log.query.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.log.common.exception.LogCommonException;
import stanl_2.final_backend.domain.log.common.exception.LogErrorCode;
import stanl_2.final_backend.domain.log.query.dto.LogDTO;
import stanl_2.final_backend.domain.log.query.dto.LogExcelDTO;
import stanl_2.final_backend.domain.log.query.dto.LogSearchDTO;
import stanl_2.final_backend.domain.log.query.repository.LogMapper;
import stanl_2.final_backend.global.excel.ExcelUtilsV1;

import java.util.List;

@Slf4j
@Service(value = "queryLogService")
public class LogQueryServiceImpl implements LogQueryService {

    private final LogMapper logMapper;
    private final ExcelUtilsV1 excelUtilsV1;

    @Autowired
    public LogQueryServiceImpl(LogMapper logMapper,
                               ExcelUtilsV1 excelUtilsV1) {
        this.logMapper = logMapper;
        this.excelUtilsV1 = excelUtilsV1;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LogDTO> selectLogs(Pageable pageable, LogSearchDTO searchLogDTO) {

        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        // 정렬 정보 가져오기
        Sort sort = pageable.getSort();
        String sortField = null;
        String sortOrder = null;
        if (sort.isSorted()) {
            sortField = sort.iterator().next().getProperty();
            sortOrder = sort.iterator().next().isAscending() ? "ASC" : "DESC";
        }

        List<LogDTO> logs = logMapper.findLogs(offset, size, searchLogDTO, sortField, sortOrder);

        int totalElements = logMapper.findLogsCnt(searchLogDTO);
        return new PageImpl<>(logs, pageable, totalElements);
    }

    @Override
    public void exportLogToExcel(HttpServletResponse response) {

        List<LogExcelDTO> logExcels = logMapper.findLogForExcel();

        if(logExcels == null) {
            throw new LogCommonException(LogErrorCode.LOG_NOT_FOUND);
        }

        excelUtilsV1.download(LogExcelDTO.class, logExcels, "logExcel", response);
    }
}
