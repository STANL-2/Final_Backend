package stanl_2.final_backend.domain.schedule.command.application.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.schedule.command.application.dto.request.ScheduleRegistRequestDTO;
import stanl_2.final_backend.domain.schedule.command.application.dto.response.ScheduleRegistResponseDTO;
import stanl_2.final_backend.domain.schedule.command.domain.aggregate.entity.Schedule;
import stanl_2.final_backend.domain.schedule.command.domain.repository.ScheduleRepository;

@Slf4j
@Service("commandScheduleServiceImpl")
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, ModelMapper modelMapper) {
        this.scheduleRepository = scheduleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ScheduleRegistResponseDTO registSchedule(ScheduleRegistRequestDTO scheduleRegistRequestDTO) {

        Schedule schedule = modelMapper.map(scheduleRegistRequestDTO,Schedule.class);

        scheduleRepository.save(schedule);

        ScheduleRegistResponseDTO scheduleRegistResponseDTO = modelMapper.map(schedule, ScheduleRegistResponseDTO.class);

        return scheduleRegistResponseDTO;
    }
}
