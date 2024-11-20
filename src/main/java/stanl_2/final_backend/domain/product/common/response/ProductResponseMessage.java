package stanl_2.final_backend.domain.product.common.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProductResponseMessage {
    private int httpStatus;
    private String msg;
    private Object result;
}
