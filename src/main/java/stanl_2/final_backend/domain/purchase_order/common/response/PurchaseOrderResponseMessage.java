package stanl_2.final_backend.domain.purchase_order.common.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PurchaseOrderResponseMessage {
    private int httpStatus;
    private String msg;
    private Object result;
}