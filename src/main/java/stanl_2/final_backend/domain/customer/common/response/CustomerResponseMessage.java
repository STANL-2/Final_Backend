package stanl_2.final_backend.domain.customer.common.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CustomerResponseMessage {
    private int httpStatus;
    private String msg;
    private Object result;
}