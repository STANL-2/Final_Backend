package stanl_2.final_backend.domain.career.common.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CareerResponseMessage {
    private int httpStatus;
    private String msg;
    private Object result;
}