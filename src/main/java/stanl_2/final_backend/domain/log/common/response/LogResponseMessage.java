package stanl_2.final_backend.domain.log.common.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LogResponseMessage {
    private int httpStatus;
    private String msg;
    private Object result;
}