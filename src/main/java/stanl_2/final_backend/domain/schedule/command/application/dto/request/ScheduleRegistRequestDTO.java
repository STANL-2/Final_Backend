package stanl_2.final_backend.domain.schedule.command.application.dto.request;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table
public class ScheduleRegistRequestDTO {

    private String name;
    private String content;
    private String reservationTime;
    private String memberId;

}
