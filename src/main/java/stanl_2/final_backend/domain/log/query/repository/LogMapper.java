package stanl_2.final_backend.domain.log.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stanl_2.final_backend.domain.log.query.dto.LogDTO;
import stanl_2.final_backend.domain.log.query.dto.LogExcelDTO;
import stanl_2.final_backend.domain.log.query.dto.LogSearchDTO;

import java.util.List;

@Mapper
public interface LogMapper {
    List<LogDTO> findLogs(
            @Param("offset") int offset,
            @Param("size") int size,
            @Param("searchLogDTO") LogSearchDTO searchLogDTO,
            @Param("sortField") String sortField,
            @Param("sortOrder") String sortOrder
    );

    int findLogsCnt(@Param("searchLogDTO") LogSearchDTO searchLogDTO);

    List<LogExcelDTO> findLogForExcel();
}
