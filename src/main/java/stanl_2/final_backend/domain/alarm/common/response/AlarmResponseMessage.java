package stanl_2.final_backend.domain.alarm.common.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AlarmResponseMessage {
    private Integer httpStatus;
    private String msg;
    private Object result;
}