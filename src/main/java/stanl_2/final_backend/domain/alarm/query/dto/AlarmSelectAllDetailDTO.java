package stanl_2.final_backend.domain.alarm.query.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AlarmSelectAllDetailDTO {

    List<AlarmSelectDetailDTO> readAlarmList;
    List<AlarmSelectDetailDTO> notReadAlarmList;

}
