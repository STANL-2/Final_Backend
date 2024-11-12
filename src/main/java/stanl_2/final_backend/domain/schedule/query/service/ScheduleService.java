package stanl_2.final_backend.domain.schedule.query.service;

import stanl_2.final_backend.domain.schedule.query.dto.ScheduleDTO;
import stanl_2.final_backend.domain.schedule.query.dto.ScheduleYearMonthDTO;

import java.util.List;

public interface ScheduleService {
    List<ScheduleDTO> selectAllSchedule(String memberId);

    List<ScheduleYearMonthDTO> selectYearMonthSchedule(ScheduleYearMonthDTO scheduleYearMonthDTO);

    ScheduleDTO selectDetailSchedule(ScheduleDTO scheduleDTO);
}
