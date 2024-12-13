package stanl_2.final_backend.domain.center.common.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CenterResponseMessage {
    private int httpStatus;
    private String msg;
    private Object result;
}