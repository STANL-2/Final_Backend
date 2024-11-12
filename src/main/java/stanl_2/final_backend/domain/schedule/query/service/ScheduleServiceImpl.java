package stanl_2.final_backend.domain.schedule.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.schedule.command.domain.aggregate.entity.Schedule;
import stanl_2.final_backend.domain.schedule.query.dto.ScheduleDTO;
import stanl_2.final_backend.domain.schedule.query.repository.ScheduleMapper;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    public List<ScheduleDTO> selectAllSchedule(String id) {

        String currentMonth = getCurrentTime().substring(5,7);

//        List<ScheduleDTO> scheduleList = scheduleMapper.find

        return null;
    }
}
