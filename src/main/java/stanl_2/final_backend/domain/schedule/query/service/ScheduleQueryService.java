package stanl_2.final_backend.domain.schedule.query.service;

import stanl_2.final_backend.domain.schedule.query.dto.ScheduleDTO;
import stanl_2.final_backend.domain.schedule.query.dto.ScheduleDayDTO;
import stanl_2.final_backend.domain.schedule.query.dto.ScheduleDetailDTO;
import stanl_2.final_backend.domain.schedule.query.dto.ScheduleYearMonthDTO;

import java.util.List;

public interface ScheduleQueryService {
    List<ScheduleDTO> selectAllSchedule(String memberLogindId);

    List<ScheduleYearMonthDTO> selectYearMonthSchedule(ScheduleYearMonthDTO scheduleYearMonthDTO);

    ScheduleDetailDTO selectDetailSchedule(ScheduleDetailDTO scheduleDetailDTO);

    List<ScheduleDayDTO> findSchedulesByDate(String currentDay);
}
