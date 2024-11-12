package stanl_2.final_backend.domain.schedule.query.repository;

import org.apache.ibatis.annotations.Mapper;
import stanl_2.final_backend.domain.schedule.query.dto.ScheduleDTO;
import stanl_2.final_backend.domain.schedule.query.dto.ScheduleYearMonthDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface ScheduleMapper {

    List<ScheduleDTO> findSchedulesByMemberIdAndSrtAt(Map<String, Object> arg);

    List<ScheduleYearMonthDTO> findSchedulesByMemberIdAndYearMonth(Map<String, String> arg);

    ScheduleDTO findScheduleByMemberIdAndScheduleId(ScheduleDTO scheduleDTO);
}
