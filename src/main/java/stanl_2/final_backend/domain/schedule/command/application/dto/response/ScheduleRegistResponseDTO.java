package stanl_2.final_backend.domain.schedule.command.application.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleRegistResponseDTO {

    private String name;
    private String content;
    private String reservationTime;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private Boolean active;
    private String memberId;
}
