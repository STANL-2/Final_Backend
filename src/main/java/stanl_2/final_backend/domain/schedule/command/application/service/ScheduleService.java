package stanl_2.final_backend.domain.schedule.command.application.service;

import stanl_2.final_backend.domain.schedule.command.application.dto.request.ScheduleModifyRequestDTO;
import stanl_2.final_backend.domain.schedule.command.application.dto.request.ScheduleRegistRequestDTO;
import stanl_2.final_backend.domain.schedule.command.application.dto.response.ScheduleModifyResponseDTO;
import stanl_2.final_backend.domain.schedule.command.application.dto.response.ScheduleRegistResponseDTO;

public interface ScheduleService {

    ScheduleRegistResponseDTO registSchedule(ScheduleRegistRequestDTO scheduleRegistRequestDTO);

    ScheduleModifyResponseDTO modifySchedule(ScheduleModifyRequestDTO scheduleModifyRequestDTO);

    Boolean deleteSchedule(String scheduleId);
}