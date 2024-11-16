package stanl_2.final_backend.domain.order.common.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class OrderResponseMessage {
    private int httpStatus;
    private String msg;
    private Object result;
}