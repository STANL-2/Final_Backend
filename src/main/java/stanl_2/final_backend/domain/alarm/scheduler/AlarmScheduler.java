package stanl_2.final_backend.domain.alarm.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import stanl_2.final_backend.domain.alarm.aggregate.entity.Alarm;
import stanl_2.final_backend.domain.alarm.repository.EmitterRepository;
import stanl_2.final_backend.domain.alarm.service.AlarmService;
import stanl_2.final_backend.domain.schedule.query.dto.ScheduleDTO;
import stanl_2.final_backend.domain.schedule.query.dto.ScheduleDayDTO;
import stanl_2.final_backend.domain.schedule.query.service.ScheduleQueryService;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;


@Service
public class AlarmScheduler {

    private final AlarmService alarmService;
    private final ScheduleQueryService scheduleQueryService;
    private final EmitterRepository emitterRepository;
    private String  getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Autowired
    public AlarmScheduler(AlarmService alarmService, ScheduleQueryService scheduleQueryService,
                          EmitterRepository emitterRepository) {
        this.alarmService = alarmService;
        this.scheduleQueryService = scheduleQueryService;
        this.emitterRepository = emitterRepository;
    }

    @Scheduled(cron = "0 0 2 * * *")  // 매일 새벽 2시에 실행
    @Transactional
    public void alarmTodaySchedule(){

        String currentDay = getCurrentTime().substring(0,10);

        List<ScheduleDayDTO> todaySchedules = scheduleQueryService.findSchedulesByDate(currentDay);

        // 사용자 별로 알림 전송
        todaySchedules.forEach(schedule -> {
            String Hour = schedule.getStartAt().substring(11,13);
            String Minute = schedule.getStartAt().substring(14,16);

            String memberId = schedule.getMemberId();
            String type = schedule.getTag();
            String message = "[" + type + "] 금일 " + Hour + "시 " + Minute + "분에 '"
                                 + schedule.getName() + "' 일정이 있습니다";
            String redirectUrl = "/api/v1/schedule";
            String createdAt = getCurrentTime();

            alarmService.send(memberId, message, redirectUrl, type, createdAt);
        });
    }
}
