package stanl_2.final_backend.domain.schedule.command.application.service;

import stanl_2.final_backend.domain.schedule.command.application.dto.request.ScheduleModifyRequestDTO;
import stanl_2.final_backend.domain.schedule.command.application.dto.request.ScheduleRegistRequestDTO;

public interface ScheduleCommandService {

    Boolean registSchedule(ScheduleRegistRequestDTO scheduleRegistRequestDTO);

    Boolean modifySchedule(ScheduleModifyRequestDTO scheduleModifyRequestDTO);

    Boolean deleteSchedule(String scheduleId);
}