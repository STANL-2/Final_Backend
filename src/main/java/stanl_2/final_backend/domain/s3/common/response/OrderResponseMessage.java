package stanl_2.final_backend.domain.s3.common.response;

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