package stanl_2.final_backend.domain.schedule.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleDayDTO {

    private String scheduleId;
    private String name;
    private String tag;
    private String startAt;
    private String endAt;
    private String memberId;
    
    
}
