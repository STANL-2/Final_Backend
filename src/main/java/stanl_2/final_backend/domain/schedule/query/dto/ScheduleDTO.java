package stanl_2.final_backend.domain.schedule.query.dto;

import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleDTO {

    private String id;
    private String name;
    private String tag;
    private String startAt;
    private String endAt;
    private String memberId;
}
