package stanl_2.final_backend.domain.schedule.command.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.MappingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.schedule.command.application.dto.ScheduleModifyDTO;
import stanl_2.final_backend.domain.schedule.command.application.dto.ScheduleRegistDTO;
import stanl_2.final_backend.domain.schedule.command.application.service.ScheduleCommandService;
import stanl_2.final_backend.domain.schedule.command.domain.aggregate.entity.Schedule;
import stanl_2.final_backend.domain.schedule.command.domain.repository.ScheduleRepository;
import stanl_2.final_backend.domain.schedule.common.exception.ScheduleCommonException;
import stanl_2.final_backend.domain.schedule.common.exception.ScheduleErrorCode;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class ScheduleCommandServiceImpl implements ScheduleCommandService {

    private final ScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ScheduleCommandServiceImpl(ScheduleRepository scheduleRepository, ModelMapper modelMapper) {
        this.scheduleRepository = scheduleRepository;
        this.modelMapper = modelMapper;
    }

    private String  getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }


    @Override
    @Transactional
    public Boolean registSchedule(ScheduleRegistDTO scheduleRegistDTO) {

        try {
            Schedule schedule = modelMapper.map(scheduleRegistDTO, Schedule.class);

            if(schedule.getMemberId() == null){
                // 매핑 오류
                throw new ScheduleCommonException(ScheduleErrorCode.MAPPING_ERROR);
            }

            scheduleRepository.save(schedule);

            return true;
        } catch (DataIntegrityViolationException e){
            // DB 무결정 제약 조건 (NOT NULL, UNIQUE) 위반
            throw new ScheduleCommonException(ScheduleErrorCode.DATA_INTEGRITY_VIOLATION);
        } catch (Exception e) {
            // 서버 오류
            throw new ScheduleCommonException(ScheduleErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public Boolean modifySchedule(ScheduleModifyDTO scheduleModifyDTO) {

        if(scheduleModifyDTO.getMemberId() == null){
            // 향후 memberException으로 바꿀 예정
            throw new ScheduleCommonException(ScheduleErrorCode.SCHEDULE_NOT_FOUND);
        }

        Schedule schedule = scheduleRepository.findById(scheduleModifyDTO.getId())
                .orElseThrow(() -> new ScheduleCommonException(ScheduleErrorCode.SCHEDULE_NOT_FOUND));

        if(!scheduleModifyDTO.getMemberId().equals(schedule.getMemberId())){
            // 권한 오ㅋ
            throw new ScheduleCommonException(ScheduleErrorCode.AUTHORIZATION_VIOLATION);
        }

        try {
            Schedule updateSchedule = modelMapper.map(scheduleModifyDTO, Schedule.class);

            if(updateSchedule.getMemberId() == null){
                // 매핑 오류
                throw new ScheduleCommonException(ScheduleErrorCode.MAPPING_ERROR);
            }

            updateSchedule.setCreatedAt(schedule.getCreatedAt());
            updateSchedule.setActive(schedule.getActive());

            scheduleRepository.save(updateSchedule);

            return true;
        } catch (DataIntegrityViolationException e) {
        // 데이터 무결성 위반 예외 처리
            throw new ScheduleCommonException(ScheduleErrorCode.DATA_INTEGRITY_VIOLATION);
        } catch (Exception e) {
            // 서버 오류
            throw new ScheduleCommonException(ScheduleErrorCode.INTERNAL_SERVER_ERROR);
        }
}

    @Override
    @Transactional
    public Boolean deleteSchedule(String scheduleId) {

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ScheduleCommonException(ScheduleErrorCode.SCHEDULE_NOT_FOUND));

        schedule.setActive(false);
        schedule.setDeletedAt(getCurrentTime());

        try {
            scheduleRepository.save(schedule);

            return true;
        } catch (DataIntegrityViolationException e) {
            // 데이터 무결성 위반 예외 처리
            throw new ScheduleCommonException(ScheduleErrorCode.DATA_INTEGRITY_VIOLATION);
        } catch (Exception e) {
            // 서버 오류
            throw new ScheduleCommonException(ScheduleErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
