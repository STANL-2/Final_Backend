package stanl_2.final_backend.domain.schedule.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScheduleRegistDTO {

    private String name;
    private String content;
    private String tag;
    private String startAt;
    private String endAt;
    private String memberLoginId;
    private String memberId;
}
