package stanl_2.final_backend.domain.alarm.aggregate.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AlarmDTO {

    private String memberId;
    private String memberLoginId;
    private String lastEventId;

}
