package stanl_2.final_backend.domain.schedule.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.schedule.query.dto.ScheduleDTO;
import stanl_2.final_backend.domain.schedule.query.repository.ScheduleMapper;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
@Service("querScheduleServiceImpl")
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleMapper scheduleMapper;
    private Timestamp getCurrentTimestamp() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return Timestamp.from(nowKst.toInstant());
    }

    @Autowired
    public ScheduleServiceImpl(ScheduleMapper scheduleMapper) {
        this.scheduleMapper = scheduleMapper;
    }

    @Override
    public ScheduleDTO selectAllSchedule(String id) {

        return null;
    }
}
