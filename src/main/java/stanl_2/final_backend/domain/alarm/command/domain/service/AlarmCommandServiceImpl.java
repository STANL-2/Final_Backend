package stanl_2.final_backend.domain.alarm.command.domain.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import stanl_2.final_backend.domain.alarm.command.application.dto.AlarmRegistDTO;
import stanl_2.final_backend.domain.alarm.command.domain.aggregate.entity.Alarm;
import stanl_2.final_backend.domain.alarm.command.application.service.AlarmCommandService;
import stanl_2.final_backend.domain.alarm.command.domain.repository.AlarmRepository;
import stanl_2.final_backend.domain.alarm.command.domain.repository.EmitterRepository;
import stanl_2.final_backend.domain.alarm.common.exception.AlarmCommonException;
import stanl_2.final_backend.domain.alarm.common.exception.AlarmErrorCode;
import stanl_2.final_backend.domain.contract.command.application.dto.ContractAlarmDTO;
import stanl_2.final_backend.domain.contract.command.domain.aggregate.entity.Contract;
import stanl_2.final_backend.domain.member.query.dto.MemberDTO;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.domain.notices.command.application.dto.NoticeAlarmDTO;
import stanl_2.final_backend.domain.order.command.application.dto.OrderAlarmDTO;
import stanl_2.final_backend.domain.order.command.domain.aggregate.entity.Order;
import stanl_2.final_backend.domain.purchase_order.command.application.dto.PurchaseOrderAlarmDTO;
import stanl_2.final_backend.domain.purchase_order.command.domain.aggregate.entity.PurchaseOrder;
import stanl_2.final_backend.domain.schedule.common.exception.ScheduleCommonException;
import stanl_2.final_backend.domain.schedule.common.exception.ScheduleErrorCode;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AlarmCommandServiceImpl implements AlarmCommandService {

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
    public AlarmCommandServiceImpl(AlarmRepository alarmRepository, EmitterRepository emitterRepository,
                                   AuthQueryService authQueryService, MemberQueryService memberQueryService){
        this.alarmRepository = alarmRepository;
        this.emitterRepository = emitterRepository;
        this.authQueryService = authQueryService;
        this.memberQueryService = memberQueryService;
    }

    @Override
    public SseEmitter subscribe(AlarmRegistDTO alarmRegistDTO, HttpServletResponse response) {

        String lastEventId = alarmRegistDTO.getLastEventId();
        String memberId = authQueryService.selectMemberIdByLoginId(alarmRegistDTO.getMemberLoginId());
        String emitterId = memberId + "_" + System.currentTimeMillis();

        // 기존 Emitter 확인 및 삭제
        SseEmitter existingEmitter = emitterRepository.findEmitterByMemberId(memberId);
        if (existingEmitter != null) {
            emitterRepository.deleteAllByEmitterId(memberId); // 기존 Emitter 제거
            existingEmitter.complete(); // 기존 연결 종료
        }

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
    public void send(String memberId, String adminId, String contentId, String message, String redirectUrl, String tag,
                     String type, String createdAt){

        Alarm alarm = alarmRepository.save(createAlarm(memberId, adminId, contentId, message, redirectUrl,
                tag, type, createdAt));

        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithByMemberId(memberId);
        sseEmitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, alarm);
                    sendToClient(emitter, key, alarm);
                }
        );
    }

    @Override
    @Transactional
    public Alarm createAlarm(String memberId, String adminId, String contentId, String message, String redirectUrl
            , String tag, String type, String createdAt) {

        Alarm alarm = new Alarm();
        alarm.setMemberId(memberId);
        alarm.setAdminId(adminId);
        alarm.setContentId(contentId);
        alarm.setMessage(message);
        alarm.setRedirectUrl(redirectUrl);
        alarm.setTag(tag);
        alarm.setType(type);
        alarm.setReadStatus(false);
        alarm.setCreatedAt(createdAt);

        return alarm;
    }

    @Override
    @Transactional
    public void sendNoticeAlarm(NoticeAlarmDTO noticeAlarmDTO){

        List<String> memberIdList = new ArrayList<>();

        if(noticeAlarmDTO.getTag().equals("ALL")){
            // 결과 합치기
            memberIdList.addAll(memberQueryService.selectMemberByRole("EMPLOYEE"));
            memberIdList.addAll(memberQueryService.selectMemberByRole("ADMIN"));
            memberIdList.addAll(memberQueryService.selectMemberByRole("DIRECTOR"));
            memberIdList.addAll(memberQueryService.selectMemberByRole("GOD"));
            // 중복 제거
            memberIdList = new ArrayList<>(new HashSet<>(memberIdList));
        } else if (noticeAlarmDTO.getTag().equals("ADMIN")){
            memberIdList.addAll(memberQueryService.selectMemberByRole("ADMIN"));
            memberIdList.addAll(memberQueryService.selectMemberByRole("DIRECTOR"));
            memberIdList.addAll(memberQueryService.selectMemberByRole("GOD"));
        }else {
            memberIdList.addAll(memberQueryService.selectMemberByRole(noticeAlarmDTO.getTag()));
        }

        memberIdList.forEach(member -> {
            String targetId = member;
            String type = "NOTICE";
            String tag = null;
            if(noticeAlarmDTO.getClassification().equals("NORMAL")){
                tag = "일반";
            } else if(noticeAlarmDTO.getClassification().equals("GOAL")) {
                tag = "영업 목표";
            } else {
                tag = "영업 전략";
            }

            String target = null;
            if(noticeAlarmDTO.getTag().equals("ALL")){
                target = "전체";
            } else if(noticeAlarmDTO.getTag().equals("ADMIN")) {
                target = "영업관리자";
            } else {
                target = "영업담당자";
            }

            String message =  target + " 대상 공지사항이 등록되었습니다.";
            String redirectUrl = "/notice/detail?tag=" + noticeAlarmDTO.getTag() + "&classification=" + noticeAlarmDTO.getClassification()
                    + "&noticeTitle=" + noticeAlarmDTO.getTitle() + "&noticeContent=" + noticeAlarmDTO.getContent()
                    + "&noticeId=" + noticeAlarmDTO.getNoticeId();
            String createdAt = getCurrentTime();

            send(targetId, noticeAlarmDTO.getMemberId(), noticeAlarmDTO.getNoticeId(), message, redirectUrl, tag, type, createdAt);
        });
    }

    @Override
    @Transactional
    public void sendContractAlarm(ContractAlarmDTO contractAlarmDTO) throws GeneralSecurityException {

        String type = "CONTRACT";
        String tag = "계약서";
        String message = contractAlarmDTO.getCustomerName() +" 고객님의 계약서가 승인되었습니다.";

        String redirectUrl = null;
        String memberRole = memberQueryService.selectMemberRoleById(contractAlarmDTO.getMemberId());

        // 권한에 따른 경로 변경
        if (memberRole == "EMPLOYEE") {
            redirectUrl = "/contract/emlist";
        } else if (memberRole == "ADMIN") {
            redirectUrl = "/contract/Elist";
        } else if (memberRole == "DIRECTOR") {
            redirectUrl = "/contract/dlist";
        } else {
            redirectUrl = "/contract/emlist";
        }

        String createdAt = getCurrentTime();

        send(contractAlarmDTO.getMemberId(), contractAlarmDTO.getAdminId(),contractAlarmDTO.getContractId(), message,
                redirectUrl, tag, type, createdAt);
    }

    @Override
    @Transactional
    public void sendPurchaseOrderAlarm(PurchaseOrderAlarmDTO purchaseOrderAlarmDTO) throws GeneralSecurityException {

        String type = "CONTRACT";
        String tag = "발주서";
        String message = purchaseOrderAlarmDTO.getTitle() +"  가 승인되었습니다.";

        String redirectUrl = null;
        String memberRole = memberQueryService.selectMemberRoleById(purchaseOrderAlarmDTO.getMemberId());

        // 권한에 따른 경로 변경
        if (memberRole == "EMPLOYEE") {
            redirectUrl = "/purchaseOrder/emlist";
        } else if (memberRole == "ADMIN"){
            redirectUrl = "/purchaseOrder/Elist";
        } else if (memberRole == "DIRECTOR"){
            redirectUrl = "/purchaseOrder/dlist";
        } else {
            redirectUrl = "/purchaseOrder/emlist";
        }

        String createdAt = getCurrentTime();

        send(purchaseOrderAlarmDTO.getMemberId(), purchaseOrderAlarmDTO.getAdminId(), purchaseOrderAlarmDTO.getPurchaseOrderId(),
                message, redirectUrl, tag, type, createdAt);
    }

    @Override
    @Transactional
    public void sendOrderAlarm(OrderAlarmDTO orderAlarmDTO) throws GeneralSecurityException {

        String type = "CONTRACT";
        String tag = "수주서";
        String message = orderAlarmDTO.getTitle() +" 가 승인되었습니다.";

        String redirectUrl = null;
        String memberRole = memberQueryService.selectMemberRoleById(orderAlarmDTO.getMemberId());

        // 권한에 따른 경로 변경
        if (memberRole == "EMPLOYEE") {
            redirectUrl = "/order/emlist";
        } else if(memberRole == "ADMIN") {
            redirectUrl = "/order/Elist";
        } else if (memberRole == "DIRECTOR") {
            redirectUrl = "/order/dlist";
        } else {
            redirectUrl = "/order/emlist";
        }
        String createdAt = getCurrentTime();

        send(orderAlarmDTO.getMemberId(), orderAlarmDTO.getAdminId(),orderAlarmDTO.getOrderId(), message, redirectUrl,
                tag, type, createdAt);
    }

    @Override
    @Transactional
    public Boolean updateReadStatus(String alarmId) {

        Alarm alarm = alarmRepository.findByAlarmId(alarmId);

        if(alarm == null){
            throw new AlarmCommonException(AlarmErrorCode.ALARM_NOT_FOUND);
        }

        try {
            alarm.setReadStatus(true);

            alarmRepository.save(alarm);
            return true;
        } catch (DataIntegrityViolationException e) {
            // 데이터 무결성 위반 예외 처리
            throw new AlarmCommonException(AlarmErrorCode.DATA_INTEGRITY_VIOLATION);
        } catch (Exception e) {
            // 서버 오류
            throw new AlarmCommonException(AlarmErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
