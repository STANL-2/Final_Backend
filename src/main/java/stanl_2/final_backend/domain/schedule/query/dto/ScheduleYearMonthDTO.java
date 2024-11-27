package stanl_2.final_backend.domain.schedule.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleYearMonthDTO {

    private String scheduleId;
    private String name;
    private String content;
    private String tag;
    private String startAt;
    private String endAt;
    private String memberId;
    private String memberLoginId;

    private String year;
    private String month;
}
