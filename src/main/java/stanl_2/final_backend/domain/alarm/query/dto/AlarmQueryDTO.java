package stanl_2.final_backend.domain.alarm.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AlarmQueryDTO {

    private String alarmId;
    private String message;
    private String redirectUrl;
    private String type;
    private Boolean readStatus;
    private String createAt;
    private String memberId;

}
