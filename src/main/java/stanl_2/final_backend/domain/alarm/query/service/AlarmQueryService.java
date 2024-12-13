package stanl_2.final_backend.domain.alarm.query.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import stanl_2.final_backend.domain.alarm.query.dto.AlarmSelectDTO;
import stanl_2.final_backend.domain.alarm.query.dto.AlarmSelectReadDTO;
import stanl_2.final_backend.domain.alarm.query.dto.AlarmSelectTypeDTO;
import stanl_2.final_backend.domain.alarm.query.dto.AlarmSelectUnreadDTO;

public interface AlarmQueryService {
    AlarmSelectTypeDTO selectMemberByAlarmType(String memberLoginId);

    Page<AlarmSelectReadDTO> selectReadAlarmByType(AlarmSelectReadDTO alarmSelectReadDTO, Pageable pageable);

    Page<AlarmSelectUnreadDTO> selectUnreadAlarmByType(AlarmSelectUnreadDTO alarmSelectUnreadDTO, Pageable pageable);

    Page<AlarmSelectDTO> selectAlarmByType(AlarmSelectDTO alarmSelectDTO, Pageable pageable);
}
