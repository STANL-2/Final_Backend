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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Map<String, String> arg = new HashMap<>();
        arg.put("memberId",memberId);
        arg.put("month",currentMonth);

        List<ScheduleDTO> scheduleList = scheduleMapper.findSchedulesByMemberIdAndSrtAt(arg);

        // Mapping 오류 체크 고려하기

        return scheduleList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleYearMonthDTO> selectYearMonthSchedule(ScheduleYearMonthDTO scheduleYearMonthDTO) {

        if(scheduleYearMonthDTO.getMemberId() == null || scheduleYearMonthDTO.getMemberId().trim().isEmpty()){
            // 향후 Member의 ErrorCode로 수정할 예정
            throw new ScheduleCommonException(ScheduleErrorCode.SCHEDULE_NOT_FOUND);
        }

        String yearMonth = scheduleYearMonthDTO.getYear() + "-" + scheduleYearMonthDTO.getMonth();

        Map<String, String> arg = new HashMap<>();
        arg.put("memberId", scheduleYearMonthDTO.getMemberId());
        arg.put("yearMonth", yearMonth);

        List<ScheduleYearMonthDTO> scheduleList =
                scheduleMapper.findSchedulesByMemberIdAndYearMonth(arg);

        // Mapping 오류 체크 고려하기

        return scheduleList;
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

        Map<String, String> arg = new HashMap<>();
        arg.put("memberId", scheduleDetailDTO.getMemberId());
        arg.put("scheduleId", scheduleDetailDTO.getId());

        ScheduleDetailDTO responseDetailSchedule
                = scheduleMapper.findScheduleByMemberIdAndScheduleId(arg);

        // Mapping 오류 체크 고려하기

        return responseDetailSchedule;
    }
}
