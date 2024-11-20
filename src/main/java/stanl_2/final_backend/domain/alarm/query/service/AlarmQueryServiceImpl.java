package stanl_2.final_backend.domain.alarm.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.alarm.common.exception.AlarmCommonException;
import stanl_2.final_backend.domain.alarm.common.exception.AlarmErrorCode;
import stanl_2.final_backend.domain.alarm.query.dto.AlarmQueryDTO;
import stanl_2.final_backend.domain.alarm.query.dto.CursorDTO;
import stanl_2.final_backend.domain.alarm.query.repository.AlarmMapper;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;

import java.util.List;

@Service
public class AlarmQueryServiceImpl implements AlarmQueryService{

    private final AlarmMapper alarmMapper;
    private final AuthQueryService authQueryService;

    @Autowired
    public AlarmQueryServiceImpl(AlarmMapper alarmMapper, AuthQueryService authQueryService) {
        this.alarmMapper = alarmMapper;
        this.authQueryService = authQueryService;
    }

    @Override
    public CursorDTO readMemberAlarms(CursorDTO cursorDTO, String memberLoginId) {

        String memberId = authQueryService.selectMemberIdByLoginId(memberLoginId);

        Integer fetchSize = cursorDTO.getSize();
        List<AlarmQueryDTO> alarmList;

        if (cursorDTO.getCursorId() == null) {
            alarmList = alarmMapper.findAlarmsByMemberId(memberId, fetchSize);
        } else {
            alarmList = alarmMapper.findAlarmsByMemberIdAndCursorId(
                    memberId, cursorDTO.getCursorId(), fetchSize
            );
        }

        if (alarmList.isEmpty()) {
            throw new AlarmCommonException(AlarmErrorCode.ALARM_NOT_FOUND);
        }

        // 실제 데이터 수가 요청한 size보다 큰 경우 다음 페이지가 있다고 판단
        Boolean hasNext = alarmList.size() > cursorDTO.getSize();

        // 데이터가 size + 1개로 조회되었으므로 마지막 데이터 제거
        if (hasNext) {
            alarmList.remove(alarmList.size() - 1);
        }

        // 마지막 알림 번호
        Long lastCursorId = alarmList.isEmpty() ? null :
                Long.parseLong(alarmList.get(alarmList.size() - 1).getAlarmId().replace("ALR_", ""));

        CursorDTO cursorResponseDTO = new CursorDTO();
        cursorResponseDTO.setCursorId(lastCursorId);
        cursorResponseDTO.setHasNext(hasNext);
        cursorResponseDTO.setComment(alarmList);

        return cursorResponseDTO;
    }
}
