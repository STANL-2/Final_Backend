package stanl_2.final_backend.domain.alarm.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import stanl_2.final_backend.domain.alarm.aggregate.dto.AlarmDTO;
import stanl_2.final_backend.domain.alarm.aggregate.entity.Alarm;
import stanl_2.final_backend.domain.alarm.repository.AlarmRepository;
import stanl_2.final_backend.domain.alarm.repository.EmitterRepository;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.domain.notices.command.application.dto.NoticeAlarmDTO;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AlarmServiceImpl implements AlarmService {

    private final EmitterRepository emitterRepository;
    private final AlarmRepository alarmRepository;
    private final AuthQueryService authQueryService;
    private final MemberQueryService memberQueryService;

    private String  getCurrentTime() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    @Autowired
    public AlarmServiceImpl(AlarmRepository alarmRepository, EmitterRepository emitterRepository,
                            AuthQueryService authQueryService, MemberQueryService memberQueryService){
        this.alarmRepository = alarmRepository;
        this.emitterRepository = emitterRepository;
        this.authQueryService = authQueryService;
        this.memberQueryService = memberQueryService;
    }

    @Override
    public SseEmitter subscribe(AlarmDTO alarmDTO, HttpServletResponse response) {

        String lastEventId = alarmDTO.getLastEventId();
        String memberId = authQueryService.selectMemberIdByLoginId(alarmDTO.getMemberLoginId());
        String emitterId = memberId + "_" + System.currentTimeMillis();

        // 클라이언트의 sse 연결 요청에 응답하기 위한 SseEmitter 객체 생성
        // 유효시간 지정으로 시간이 지나면 클라이언트에서 자동으로 재연결 요청함
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));
        response.setHeader("X-Accel-Buffering", "no"); // NGINX PROXY 에서의 필요설정 불필요한 버퍼링방지

        // SseEmitter의 완료/시간초과/에러로 인한 전송 불가 시 sseEmitter 삭제
        emitter.onCompletion(() -> emitterRepository.deleteAllByEmitterId(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteAllByEmitterId(emitterId));
        emitter.onError((e) -> emitterRepository.deleteAllByEmitterId(emitterId));

        // 연결 직후, 데이터 전송이 없을 시 503 에러 발생. 에러 방지 위한 더미데이터 전송
        sendToClient(emitter, emitterId,  emitterId + "님 연결되었습니다.");

        // 클라이언트가 미수신한 Event 유실 예방, 연결이 끊켰거나 미수신된 데이터를 다 찾아서 보내줌
        if (!lastEventId.isEmpty()) {
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithByMemberId(memberId);
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
        }

        return emitter;
    }

    @Override
    public void sendToClient(SseEmitter emitter, String emitterId, Object data) {

        try {
            emitter.send(SseEmitter.event()
                    .id(emitterId)
                    .name("sse")
                    .data(data));
        } catch (IOException e){
            emitterRepository.deleteAllByEmitterId(emitterId);
            log.error("SSE 연결 오류 발생", e);
        }
    }

    @Override
    @Transactional
    public void send(String memberId, String message, String redirectUrl, String type, String createdAt){

        Alarm alarm = alarmRepository.save(createAlarm(memberId, message, redirectUrl, type, createdAt));

        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithByMemberId(memberId);
        sseEmitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, alarm);
                    sendToClient(emitter, key, alarm);
                }
        );
    }

    @Override
    public Alarm createAlarm(String memberId, String message, String redirectUrl, String type, String createdAt) {

        Alarm alarm = new Alarm();
        alarm.setMemberId(memberId);
        alarm.setMessage(message);
        alarm.setRedirectUrl(redirectUrl);
        alarm.setType(type);
        alarm.setReadStatus(false);
        alarm.setCreatedAt(createdAt);

        return alarm;
    }

    @Override
    public void sendNoticeAlarm(NoticeAlarmDTO noticeAlarmDTO){

        List<String> memberIdList = memberQueryService.selectMemberByRole(noticeAlarmDTO.getTag());

        memberIdList.forEach(member -> {
            String memberId = member;
            String type = "NOTICE";
            String tag = noticeAlarmDTO.getClassification();
            String message = "[" + tag + "] 영업 관리자 대상 공지사항이 등록되었습니다.";
            String redirectUrl = "/api/v1/notice" + noticeAlarmDTO.getNoticeId();
            String createdAt = getCurrentTime();

            send(memberId, message, redirectUrl, type, createdAt);
        });
    }

}
