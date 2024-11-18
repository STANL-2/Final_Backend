package stanl_2.final_backend.domain.schedule.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScheduleDeleteDTO {

    private String scheduleId;
    private String memberLoginId;
}
