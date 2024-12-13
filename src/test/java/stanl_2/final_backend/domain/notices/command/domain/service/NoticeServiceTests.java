package stanl_2.final_backend.domain.notices.command.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import stanl_2.final_backend.domain.alarm.command.application.service.AlarmCommandService;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.member.query.service.MemberQueryService;
import stanl_2.final_backend.domain.notices.command.application.dto.NoticeAlarmDTO;
import stanl_2.final_backend.domain.notices.command.application.dto.NoticeDeleteDTO;
import stanl_2.final_backend.domain.notices.command.application.dto.NoticeRegistDTO;
import stanl_2.final_backend.domain.notices.command.domain.aggragate.entity.Notice;
import stanl_2.final_backend.domain.notices.command.domain.repository.NoticeRepository;
import stanl_2.final_backend.global.redis.RedisService;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NoticeServiceTests {

    @InjectMocks
    private NoticeCommandServiceImpl noticeCommandService;

    @Mock
    private NoticeRepository noticeRepository;

    @Mock
    private RedisService redisService;

    @Mock
    private AuthQueryService authQueryService;

    @Mock
    private AlarmCommandService alarmCommandService;

    @Mock
    private MemberQueryService memberQueryService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private Principal principal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 게시글_등록() throws Exception {
        NoticeRegistDTO registDTO = new NoticeRegistDTO();
        registDTO.setMemberLoginId("loginId");
        Notice notice = new Notice();
        when(modelMapper.map(registDTO, Notice.class)).thenReturn(notice);
        when(noticeRepository.save(notice)).thenReturn(notice);
        NoticeAlarmDTO alarmDTO = new NoticeAlarmDTO();
        when(modelMapper.map(notice, NoticeAlarmDTO.class)).thenReturn(alarmDTO);


        noticeCommandService.registerNotice(registDTO, principal);


        verify(alarmCommandService).sendNoticeAlarm(alarmDTO);
    }



    @Test
    void 게시글_삭제() {
        // Arrange
        NoticeDeleteDTO deleteDTO = new NoticeDeleteDTO();
        deleteDTO.setNoticeId("validId");

        Notice notice = new Notice();
        notice.setMemberId("memberId");
        when(noticeRepository.findByNoticeId("validId")).thenReturn(Optional.of(notice));
        when(principal.getName()).thenReturn("memberId");

        // Act
        noticeCommandService.deleteNotice(deleteDTO, principal);

        // Assert
        verify(noticeRepository).save(notice);
        assertNotNull(notice.getDeletedAt());
    }
}
