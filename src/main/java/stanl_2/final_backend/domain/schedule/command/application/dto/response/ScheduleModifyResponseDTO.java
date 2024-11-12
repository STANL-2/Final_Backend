package stanl_2.final_backend.domain.schedule.command.application.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleModifyResponseDTO {

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
