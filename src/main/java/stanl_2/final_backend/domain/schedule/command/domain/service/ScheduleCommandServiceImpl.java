package stanl_2.final_backend.domain.schedule.command.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.MappingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.schedule.command.application.dto.request.ScheduleModifyRequestDTO;
import stanl_2.final_backend.domain.schedule.command.application.dto.request.ScheduleRegistRequestDTO;
import stanl_2.final_backend.domain.schedule.command.application.service.ScheduleCommandService;
import stanl_2.final_backend.domain.schedule.command.domain.aggregate.entity.Schedule;
import stanl_2.final_backend.domain.schedule.command.domain.repository.ScheduleRepository;
import stanl_2.final_backend.domain.schedule.common.exception.CommonException;
import stanl_2.final_backend.domain.schedule.common.exception.ErrorCode;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
@Service("commandScheduleServiceImpl")
public class ScheduleCommandServiceImpl implements ScheduleCommandService {

    private final ScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ScheduleCommandServiceImpl(ScheduleRepository scheduleRepository, ModelMapper modelMapper) {
        this.scheduleRepository = scheduleRepository;
        this.modelMapper = modelMapper;
    }

    private Timestamp getCurrentTimestamp() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return Timestamp.from(nowKst.toInstant());
    }

    @Override
    @Transactional
    public Boolean registSchedule(ScheduleRegistRequestDTO scheduleRegistRequestDTO) {

        try {
            Schedule schedule = modelMapper.map(scheduleRegistRequestDTO, Schedule.class);

            scheduleRepository.save(schedule);

            return true;
        } catch (DataIntegrityViolationException e){
            // DB 무결정 제약 조건 (NOT NULL, UNIQUE) 위반
            throw new CommonException(ErrorCode.DATA_INTEGRITY_VIOLATION);
        } catch (MappingException e){
            // ModelMapper 매핑 오류
            throw new CommonException(ErrorCode.MAPPING_ERROR);
        } catch (Exception e) {
            // 서버 오류
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public Boolean modifySchedule(ScheduleModifyRequestDTO scheduleModifyRequestDTO) {

        Schedule schedule = scheduleRepository.findById(scheduleModifyRequestDTO.getId())
                .orElseThrow(() -> new CommonException(ErrorCode.SCHEDULE_NOT_FOUND));

        try {
            Schedule updateSchedule = modelMapper.map(scheduleModifyRequestDTO, Schedule.class);
            updateSchedule.setCreatedAt(schedule.getCreatedAt());
            updateSchedule.setActive(schedule.getActive());

            scheduleRepository.save(updateSchedule);

            return true;
        } catch (MappingException e){
            throw new CommonException(ErrorCode.MAPPING_ERROR);
        }
    }

    @Override
    public Boolean deleteSchedule(String scheduleId) {

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CommonException(ErrorCode.SCHEDULE_NOT_FOUND));

        schedule.setActive(false);
        schedule.setDeletedAt(getCurrentTimestamp());

        scheduleRepository.save(schedule);

        return true;
    }
}
