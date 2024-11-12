package stanl_2.final_backend.domain.schedule.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleYearMonthDTO {

    private String id;
    private String content;
    private String startAt;
    private String endAt;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private Boolean active;
    private String memberId;

    private String year;
    private String month;
}
