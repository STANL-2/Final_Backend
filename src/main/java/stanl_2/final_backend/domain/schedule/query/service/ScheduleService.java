package stanl_2.final_backend.domain.schedule.query.service;

import stanl_2.final_backend.domain.schedule.query.dto.ScheduleDTO;

public interface ScheduleService {
    ScheduleDTO selectAllSchedule(String id);
}
