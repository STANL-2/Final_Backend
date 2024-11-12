package stanl_2.final_backend.domain.schedule.query.service;

import stanl_2.final_backend.domain.schedule.query.dto.ScheduleDTO;

import java.util.List;

public interface ScheduleService {
    List<ScheduleDTO> selectAllSchedule(String id);
}
