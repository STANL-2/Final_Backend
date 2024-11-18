package stanl_2.final_backend.domain.schedule.command.application.service;

import stanl_2.final_backend.domain.schedule.command.application.dto.ScheduleDeleteDTO;
import stanl_2.final_backend.domain.schedule.command.application.dto.ScheduleModifyDTO;
import stanl_2.final_backend.domain.schedule.command.application.dto.ScheduleRegistDTO;

public interface ScheduleCommandService {

    Boolean registSchedule(ScheduleRegistDTO scheduleRegistDTO);

    Boolean modifySchedule(ScheduleModifyDTO scheduleModifyDTO);

    Boolean deleteSchedule(ScheduleDeleteDTO scheduleDeleteDTO);
}