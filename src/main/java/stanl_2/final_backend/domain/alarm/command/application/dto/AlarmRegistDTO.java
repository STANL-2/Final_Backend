package stanl_2.final_backend.domain.alarm.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AlarmRegistDTO {

    private String memberId;
    private String memberLoginId;
    private String lastEventId;

}
