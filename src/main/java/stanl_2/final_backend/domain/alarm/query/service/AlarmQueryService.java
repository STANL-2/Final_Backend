package stanl_2.final_backend.domain.alarm.query.service;

import org.springframework.data.domain.Pageable;
import stanl_2.final_backend.domain.alarm.query.dto.AlarmSelectAllDetailDTO;
import stanl_2.final_backend.domain.alarm.query.dto.AlarmSelectDetailDTO;
import stanl_2.final_backend.domain.alarm.query.dto.AlarmSelectTypeDTO;

import java.util.List;

public interface AlarmQueryService {
    AlarmSelectTypeDTO selectMemberByAlarmType(String memberLoginId);

    AlarmSelectAllDetailDTO selectDetailAlarmByType(AlarmSelectDetailDTO alarmSelectDetailDTO, Pageable pageable);
}
