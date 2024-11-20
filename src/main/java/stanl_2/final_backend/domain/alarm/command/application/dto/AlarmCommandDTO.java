package stanl_2.final_backend.domain.alarm.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AlarmCommandDTO {

    private String memberId;
    private String memberLoginId;
    private String lastEventId;

}
