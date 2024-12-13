package stanl_2.final_backend.domain.notices.command.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.alarm.command.application.service.AlarmCommandService;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.domain.notices.command.application.dto.NoticeAlarmDTO;
import stanl_2.final_backend.domain.notices.command.application.dto.NoticeDeleteDTO;
import stanl_2.final_backend.domain.notices.command.application.dto.NoticeModifyDTO;
import stanl_2.final_backend.domain.notices.command.application.dto.NoticeRegistDTO;
import stanl_2.final_backend.domain.notices.command.application.service.NoticeCommandService;
import stanl_2.final_backend.domain.notices.command.domain.aggragate.entity.Notice;
import stanl_2.final_backend.domain.notices.command.domain.repository.NoticeRepository;
import stanl_2.final_backend.domain.notices.common.exception.NoticeCommonException;
import stanl_2.final_backend.domain.notices.common.exception.NoticeErrorCode;
import stanl_2.final_backend.global.redis.RedisService;

import java.security.GeneralSecurityException;
import java.security.Principal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.transaction.support.TransactionSynchronizationManager.isCurrentTransactionReadOnly;
@Slf4j
@Service("commandNoticeService")
public class NoticeCommandServiceImpl implements NoticeCommandService {

    private final NoticeRepository noticeRepository;

    private final RedisService redisService;
    private final AuthQueryService authQueryService;

    private final ModelMapper modelMapper;
    private final AlarmCommandService alarmCommandService;
    private final MemberQueryService memberQueryService;

    @Autowired
    public NoticeCommandServiceImpl(NoticeRepository noticeRepository, ModelMapper modelMapper,
                                    AuthQueryService authQueryService, AlarmCommandService alarmCommandService,
                                    RedisService redisService, MemberQueryService memberQueryService) {
        this.noticeRepository = noticeRepository;
        this.modelMapper = modelMapper;
        this.authQueryService =authQueryService;
        this.alarmCommandService = alarmCommandService;
        this.redisService = redisService;
        this.memberQueryService = memberQueryService;
    }

    private String getCurrentTimestamp() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    @Transactional(readOnly = false)
    public void registerNotice(NoticeRegistDTO noticeRegistDTO,
                               Principal principal) throws GeneralSecurityException {
        System.out.println("[Before Cache Clear] Transaction ReadOnly: " + isCurrentTransactionReadOnly());
        redisService.clearNoticeCache();
        String memberId = authQueryService.selectMemberIdByLoginId(noticeRegistDTO.getMemberLoginId());
        memberId=memberQueryService.selectNameById(memberId);
        noticeRegistDTO.setMemberId(memberId);
        try {
            Notice notice = modelMapper.map(noticeRegistDTO, Notice.class);
            Notice newNotice = noticeRepository.save(notice);
            NoticeAlarmDTO noticeAlarmDTO = modelMapper.map(newNotice, NoticeAlarmDTO.class);
            alarmCommandService.sendNoticeAlarm(noticeAlarmDTO);
        } catch (DataIntegrityViolationException e){
            // DB 무결정 제약 조건 (NOT NULL, UNIQUE) 위반
            throw new NoticeCommonException(NoticeErrorCode.DATA_INTEGRITY_VIOLATION);
        } catch (Exception e) {
            // 서버 오류
            throw new NoticeCommonException(NoticeErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public NoticeModifyDTO modifyNotice(String id, NoticeModifyDTO noticeModifyDTO,Principal principal) throws GeneralSecurityException {

        redisService.clearNoticeCache();
        String memberId = authQueryService.selectMemberIdByLoginId(noticeModifyDTO.getMemberLoginId());
        memberId=memberQueryService.selectNameById(memberId);

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeCommonException(NoticeErrorCode.NOTICE_NOT_FOUND));
        if(!notice.getMemberId().equals(memberId)){
            // 권한 오류
            throw new NoticeCommonException(NoticeErrorCode.AUTHORIZATION_VIOLATION);
        }
        try {
            Notice updateNotice = modelMapper.map(noticeModifyDTO, Notice.class);
            updateNotice.setNoticeId(notice.getNoticeId());
            updateNotice.setMemberId(notice.getMemberId());
            updateNotice.setCreatedAt(notice.getCreatedAt());
            updateNotice.setActive(notice.getActive());

            noticeRepository.save(updateNotice);

            NoticeModifyDTO noticeModify = modelMapper.map(updateNotice,NoticeModifyDTO.class);

            return noticeModify;
        } catch (DataIntegrityViolationException e) {
            // 데이터 무결성 위반 예외 처리
            throw new NoticeCommonException(NoticeErrorCode.DATA_INTEGRITY_VIOLATION);
        } catch (Exception e) {
            // 서버 오류
            throw new NoticeCommonException(NoticeErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public void deleteNotice(NoticeDeleteDTO noticeDeleteDTO, Principal principal) {
        redisService.clearNoticeCache();
        String memberId = principal.getName();

        Notice notice = noticeRepository.findByNoticeId(noticeDeleteDTO.getNoticeId())
                .orElseThrow(() -> new NoticeCommonException(NoticeErrorCode.NOTICE_NOT_FOUND));

        if(!memberId.equals(memberId)){
            // 권한 오류
            throw new NoticeCommonException(NoticeErrorCode.AUTHORIZATION_VIOLATION);
        }

        notice.setActive(false);
        notice.setDeletedAt(getCurrentTimestamp());

        try {
            noticeRepository.save(notice);
        } catch (DataIntegrityViolationException e) {
        // 데이터 무결성 위반 예외 처리
            throw new NoticeCommonException(NoticeErrorCode.DATA_INTEGRITY_VIOLATION);
        } catch (Exception e) {
        // 서버 오류
            throw new NoticeCommonException(NoticeErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
