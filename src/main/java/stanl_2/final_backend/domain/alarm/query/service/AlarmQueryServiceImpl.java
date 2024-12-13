package stanl_2.final_backend.domain.alarm.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.alarm.common.exception.AlarmCommonException;
import stanl_2.final_backend.domain.alarm.common.exception.AlarmErrorCode;
import stanl_2.final_backend.domain.alarm.query.dto.AlarmSelectDTO;
import stanl_2.final_backend.domain.alarm.query.dto.AlarmSelectReadDTO;
import stanl_2.final_backend.domain.alarm.query.dto.AlarmSelectTypeDTO;
import stanl_2.final_backend.domain.alarm.query.dto.AlarmSelectUnreadDTO;
import stanl_2.final_backend.domain.alarm.query.repository.AlarmMapper;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;

import java.util.List;

@Slf4j
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
    @Transactional(readOnly = true)
    public AlarmSelectTypeDTO selectMemberByAlarmType(String memberLoginId) {

        String memberId = authQueryService.selectMemberIdByLoginId(memberLoginId);

        AlarmSelectTypeDTO alarmSelectTypeDTO = alarmMapper.findNumberOfAlarmsByType(memberId);

        if(alarmSelectTypeDTO == null){
            AlarmSelectTypeDTO alarmNullSelectTypeDTO
                    = new AlarmSelectTypeDTO(0,0,0);

            return alarmNullSelectTypeDTO;
        }

        return alarmSelectTypeDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AlarmSelectReadDTO> selectReadAlarmByType(AlarmSelectReadDTO alarmSelectReadDTO, Pageable pageable) {

        Integer offset = Math.toIntExact(pageable.getOffset());
        Integer pageSize = pageable.getPageSize();

        String memberId = authQueryService.selectMemberIdByLoginId(alarmSelectReadDTO.getMemberLoginId());

        List<AlarmSelectReadDTO> readAlarmList
                = alarmMapper.findReadAlarmsByType(offset, pageSize, memberId, alarmSelectReadDTO.getType());

        Integer count = alarmMapper.findReadAlarmsCountByMemberId(memberId);
        int totalOrder = (count != null) ? count : 0;

        return new PageImpl<>(readAlarmList, pageable, totalOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AlarmSelectUnreadDTO> selectUnreadAlarmByType(AlarmSelectUnreadDTO alarmSelectUnreadDTO, Pageable pageable) {

        Integer offset = Math.toIntExact(pageable.getOffset());
        Integer pageSize = pageable.getPageSize();

        String memberId = authQueryService.selectMemberIdByLoginId(alarmSelectUnreadDTO.getMemberLoginId());

        List<AlarmSelectUnreadDTO> unReadAlarmList
                = alarmMapper.findUnReadAlarmsByType(offset, pageSize, memberId, alarmSelectUnreadDTO.getType());

        Integer count = alarmMapper.findUnreadAlarmsCountByMemberId(memberId);
        int totalOrder = (count != null) ? count : 0;

        return new PageImpl<>(unReadAlarmList, pageable, totalOrder);
    }

    @Override
    public Page<AlarmSelectDTO> selectAlarmByType(AlarmSelectDTO alarmSelectDTO, Pageable pageable) {

        Integer offset = Math.toIntExact(pageable.getOffset());
        Integer pageSize = pageable.getPageSize();

        String memberId = authQueryService.selectMemberIdByLoginId(alarmSelectDTO.getMemberLoginId());

        List<AlarmSelectDTO> readAlarmList
                = alarmMapper.findAllAlarmsByType(offset, pageSize, memberId, alarmSelectDTO.getType());

        Integer count = alarmMapper.findReadAlarmsCountByMemberId(memberId);
        int totalOrder = (count != null) ? count : 0;

        return new PageImpl<>(readAlarmList, pageable, totalOrder);

    }
}
