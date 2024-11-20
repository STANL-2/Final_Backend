package stanl_2.final_backend.domain.alarm.query.service;

import stanl_2.final_backend.domain.alarm.query.dto.CursorDTO;

public interface AlarmQueryService {
    CursorDTO readMemberAlarms(CursorDTO cursorDTO, String memberLoginId);
}
