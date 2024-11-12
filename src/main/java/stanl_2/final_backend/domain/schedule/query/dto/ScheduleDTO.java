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
    private String content;
    private String startAt;
    private String endAt;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private Boolean active;
    private String memberId;
}
