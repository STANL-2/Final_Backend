package stanl_2.final_backend.domain.alarm.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.alarm.query.dto.AlarmSelectAllDetailDTO;
import stanl_2.final_backend.domain.alarm.query.dto.AlarmSelectDetailDTO;
import stanl_2.final_backend.domain.alarm.query.dto.AlarmSelectTypeDTO;
import stanl_2.final_backend.domain.alarm.query.repository.AlarmMapper;
import stanl_2.final_backend.domain.member.query.service.AuthQueryService;
import stanl_2.final_backend.domain.order.common.exception.OrderCommonException;
import stanl_2.final_backend.domain.order.common.exception.OrderErrorCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public AlarmSelectTypeDTO selectMemberByAlarmType(String memberLoginId) {

        String memberId = authQueryService.selectMemberIdByLoginId(memberLoginId);

        AlarmSelectTypeDTO alarmSelectTypeDTO = alarmMapper.findNumberOfAlarmsByType(memberId);

        return alarmSelectTypeDTO;
    }

    @Override
    public Page<AlarmSelectAllDetailDTO> selectDetailAlarmByType(AlarmSelectDetailDTO alarmSelectDetailDTO, Pageable pageable) {

        Integer offset = Math.toIntExact(pageable.getOffset());
        Integer pageSize = pageable.getPageSize();

        String memberId = authQueryService.selectMemberIdByLoginId(alarmSelectDetailDTO.getMemberLoginId());

        List<AlarmSelectDetailDTO> readAlarmList
                = alarmMapper.findReadAlarmsByType(offset, pageSize, memberId, alarmSelectDetailDTO.getType());

        List<AlarmSelectDetailDTO> notReadAlarmList
                = alarmMapper.findNotReadAlarmsByType(offset, pageSize, memberId, alarmSelectDetailDTO.getType());

        if (readAlarmList == null || readAlarmList.isEmpty()) {
            throw new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND);
        }

        if (notReadAlarmList == null || notReadAlarmList.isEmpty()) {
            throw new OrderCommonException(OrderErrorCode.ORDER_NOT_FOUND);
        }

        AlarmSelectAllDetailDTO alarmSelectAllDetailDTO = new AlarmSelectAllDetailDTO();
        alarmSelectAllDetailDTO.setReadAlarmList(readAlarmList);
        alarmSelectAllDetailDTO.setNotReadAlarmList(notReadAlarmList);

        return new PageImpl<>(alarmSelectAllDetailDTO, pageable, 1);
    }
}
