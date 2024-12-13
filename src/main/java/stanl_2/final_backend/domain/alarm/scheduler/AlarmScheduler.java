package stanl_2.final_backend.domain.alarm.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.alarm.command.application.service.AlarmCommandService;
import stanl_2.final_backend.domain.schedule.query.dto.ScheduleDayDTO;
import stanl_2.final_backend.domain.schedule.query.service.ScheduleQueryService;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service("AlarmSchdulerService")
@Slf4j
public class AlarmScheduler {

    private final AlarmCommandService alarmCommandService;
    private final ScheduleQueryService scheduleQueryService;
    private String  getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Autowired
    public AlarmScheduler(AlarmCommandService alarmCommandService, ScheduleQueryService scheduleQueryService) {
        this.alarmCommandService = alarmCommandService;
        this.scheduleQueryService = scheduleQueryService;
    }

//    @Scheduled(cron = "0 0 2 * * *")  // 매일 새벽 2시에 실행)
    @Scheduled(cron = "0 27 14 * * *")
    @Transactional
    public void alarmTodaySchedule(){

        String currentDay = getCurrentTime().substring(0,10);

        List<ScheduleDayDTO> todaySchedules = scheduleQueryService.findSchedulesByDate(currentDay);

        // 사용자 별로 알림 전송
        todaySchedules.forEach(schedule -> {
            String Hour = schedule.getStartAt().substring(11,13);
            String Minute = schedule.getStartAt().substring(14,16);

            String memberId = schedule.getMemberId();
            String type = "SCHEDULE";

            String tag = null;
            if(schedule.getTag().equals("MEETING")){
                tag = "미팅";
            } else if(schedule.getTag().equals("SESSION")){
                tag = "회의";
            } else if(schedule.getTag().equals("VACATION")){
                tag = "휴가";
            } else{
                tag = "교육";
            }

            String message = "금일 " + Hour + "시 " + Minute + "분에 '" + tag + "' 일정이 있습니다";
            String redirectUrl = "/schedule";
            String createdAt = getCurrentTime();

            alarmCommandService.send(memberId, memberId, schedule.getScheduleId(), message, redirectUrl, tag, type, createdAt);
        });
    }
}
