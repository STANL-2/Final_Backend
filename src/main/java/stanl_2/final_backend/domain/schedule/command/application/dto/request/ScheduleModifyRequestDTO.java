package stanl_2.final_backend.domain.schedule.command.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScheduleModifyRequestDTO {

    private String id;
    private String name;
    private String content;
    private String startAt;
    private String endAt;
    private String memberId;
}
