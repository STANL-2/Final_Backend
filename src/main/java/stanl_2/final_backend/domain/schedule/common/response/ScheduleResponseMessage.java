package stanl_2.final_backend.domain.schedule.common.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ScheduleResponseMessage {
    private Integer httpStatus;
    private String msg;
    private Object result;
}