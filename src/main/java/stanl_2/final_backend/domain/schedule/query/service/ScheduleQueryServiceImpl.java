package stanl_2.final_backend.domain.schedule.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.schedule.common.exception.ScheduleCommonException;
import stanl_2.final_backend.domain.schedule.common.exception.ScheduleErrorCode;
import stanl_2.final_backend.domain.schedule.query.dto.ScheduleDTO;
import stanl_2.final_backend.domain.schedule.query.dto.ScheduleDetailDTO;
import stanl_2.final_backend.domain.schedule.query.dto.ScheduleYearMonthDTO;
import stanl_2.final_backend.domain.schedule.query.repository.ScheduleMapper;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class ScheduleQueryServiceImpl implements ScheduleQueryService {

    private final ScheduleMapper scheduleMapper;
    private String  getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Autowired
    public ScheduleQueryServiceImpl(ScheduleMapper scheduleMapper) {
        this.scheduleMapper = scheduleMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleDTO> selectAllSchedule(String memberId) {

        if(memberId == null || memberId.trim().isEmpty()){
            // 향후 Member의 ErrorCode로 수정할 예정
            throw new ScheduleCommonException(ScheduleErrorCode.SCHEDULE_NOT_FOUND);
        }

        String currentMonth = getCurrentTime().substring(0,7);

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setMemberId(memberId);
        scheduleDTO.setMonth(currentMonth);

        try {
            List<ScheduleDTO> scheduleList = scheduleMapper.findSchedulesByMemberIdAndSrtAt(scheduleDTO);

            if(scheduleList == null || scheduleList.isEmpty()){
                throw new ScheduleCommonException(ScheduleErrorCode.SCHEDULE_NOT_FOUND);
            }

            return scheduleList;
        } catch(DataAccessException e){
            throw new ScheduleCommonException(ScheduleErrorCode.DATA_ACCESS_ERROR);
        } catch(NullPointerException e){
            throw new ScheduleCommonException(ScheduleErrorCode.SCHEDULE_NOT_FOUND);
        } catch(Exception e){
            throw new ScheduleCommonException(ScheduleErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleYearMonthDTO> selectYearMonthSchedule(ScheduleYearMonthDTO scheduleYearMonthDTO) {

        if(scheduleYearMonthDTO.getMemberId() == null || scheduleYearMonthDTO.getMemberId().trim().isEmpty()){
            // 향후 Member의 ErrorCode로 수정할 예정
            throw new ScheduleCommonException(ScheduleErrorCode.SCHEDULE_NOT_FOUND);
        }

        if(scheduleYearMonthDTO.getId() == null || scheduleYearMonthDTO.getId().trim().isEmpty()){
            throw new ScheduleCommonException(ScheduleErrorCode.SCHEDULE_NOT_FOUND);
        }

        scheduleYearMonthDTO.setYearMonth(scheduleYearMonthDTO.getYear() + "-" + scheduleYearMonthDTO.getMonth());


        try {
            List<ScheduleYearMonthDTO> scheduleList =
                    scheduleMapper.findSchedulesByMemberIdAndYearMonth(scheduleYearMonthDTO);

            if(scheduleList == null || scheduleList.isEmpty()){
                throw new ScheduleCommonException(ScheduleErrorCode.SCHEDULE_NOT_FOUND);
            }

            return scheduleList;
        } catch(DataAccessException e){
            throw new ScheduleCommonException(ScheduleErrorCode.DATA_ACCESS_ERROR);
        } catch(NullPointerException e){
            throw new ScheduleCommonException(ScheduleErrorCode.SCHEDULE_NOT_FOUND);
        } catch(Exception e){
            throw new ScheduleCommonException(ScheduleErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleDetailDTO selectDetailSchedule(ScheduleDetailDTO scheduleDetailDTO) {

        if(scheduleDetailDTO.getMemberId() == null || scheduleDetailDTO.getMemberId().trim().isEmpty()){
            // 향후 Member의 ErrorCode로 수정할 예정
            throw new ScheduleCommonException(ScheduleErrorCode.SCHEDULE_NOT_FOUND);
        }

        if(scheduleDetailDTO.getId() == null || scheduleDetailDTO.getId().trim().isEmpty()){
            throw new ScheduleCommonException(ScheduleErrorCode.SCHEDULE_NOT_FOUND);
        }

        try {
            ScheduleDetailDTO responseDetailSchedule
                    = scheduleMapper.findScheduleByMemberIdAndScheduleId(scheduleDetailDTO);

            return responseDetailSchedule;
        } catch(DataAccessException e){
            throw new ScheduleCommonException(ScheduleErrorCode.DATA_ACCESS_ERROR);
        } catch(NullPointerException e){
            throw new ScheduleCommonException(ScheduleErrorCode.SCHEDULE_NOT_FOUND);
        } catch(Exception e){
            throw new ScheduleCommonException(ScheduleErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


}
