package stanl_2.final_backend.domain.schedule.command.application.service;

import stanl_2.final_backend.domain.schedule.command.application.dto.request.ScheduleRegistRequestDTO;
import stanl_2.final_backend.domain.schedule.command.application.dto.response.ScheduleRegistResponseDTO;

public interface ScheduleService {

    ScheduleRegistResponseDTO registSchedule(ScheduleRegistRequestDTO scheduleRegistRequestDTO);
}
