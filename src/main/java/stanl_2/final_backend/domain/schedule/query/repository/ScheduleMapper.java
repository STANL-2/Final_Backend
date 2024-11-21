package stanl_2.final_backend.domain.schedule.query.repository;

import org.apache.ibatis.annotations.Mapper;
import stanl_2.final_backend.domain.schedule.query.dto.ScheduleDTO;
import stanl_2.final_backend.domain.schedule.query.dto.ScheduleDayDTO;
import stanl_2.final_backend.domain.schedule.query.dto.ScheduleDetailDTO;
import stanl_2.final_backend.domain.schedule.query.dto.ScheduleYearMonthDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface ScheduleMapper {

    List<ScheduleYearMonthDTO> findSchedulesByMemberIdAndYearMonth(Map<String, String> arg);

    ScheduleDetailDTO findScheduleByMemberIdAndScheduleId(Map<String, String> arg);

    List<ScheduleDTO> findSchedulesByMemberIdAndStartAt(Map<String, String> arg);

    List<ScheduleDayDTO> findAllSchedulesByDay(String currentDay);
}
