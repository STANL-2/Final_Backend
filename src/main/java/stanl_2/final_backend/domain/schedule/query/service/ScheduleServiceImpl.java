package stanl_2.final_backend.domain.schedule.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.schedule.query.dto.ScheduleDTO;
import stanl_2.final_backend.domain.schedule.query.dto.ScheduleYearMonthDTO;
import stanl_2.final_backend.domain.schedule.query.repository.ScheduleMapper;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("querScheduleServiceImpl")
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleMapper scheduleMapper;
    private String  getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Autowired
    public ScheduleServiceImpl(ScheduleMapper scheduleMapper) {
        this.scheduleMapper = scheduleMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleDTO> selectAllSchedule(String memberId) {

        String currentMonth = getCurrentTime().substring(0,7);

        Map<String, Object> arg = new HashMap<>();

        arg.put("memberId",memberId);
        arg.put("month",currentMonth);

        List<ScheduleDTO> scheduleList = scheduleMapper.findSchedulesByMemberIdAndSrtAt(arg);

        return scheduleList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleYearMonthDTO> selectYearMonthSchedule(ScheduleYearMonthDTO scheduleYearMonthDTO) {

        String memberId = scheduleYearMonthDTO.getMemberId();
        String year = scheduleYearMonthDTO.getYear();
        String month = scheduleYearMonthDTO.getMonth();

        String checkDate = year + "-" + month;

        Map<String, String> arg = new HashMap<>();

        arg.put("memberId", memberId);
        arg.put("yearMonth", checkDate);

        List<ScheduleYearMonthDTO> scheduleList = scheduleMapper.findSchedulesByMemberIdAndYearMonth(arg);

        return scheduleList;
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleDTO selectDetailSchedule(ScheduleDTO scheduleDTO) {

        ScheduleDTO responseSchedule = scheduleMapper.findScheduleByMemberIdAndScheduleId(scheduleDTO);

        return responseSchedule;
    }

}
