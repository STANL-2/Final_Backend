package stanl_2.final_backend.domain.alarm.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AlarmSelectTypeDTO {

    private Integer scheduleAlarmCount;
    private Integer noticeAlarmCount;
    private Integer contractAlarmCount;
}
