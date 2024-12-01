package stanl_2.final_backend.domain.purchase_order.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PurchaseOrderStatusModifyDTO {
    private String purchaseOrderId;
    private String title;
    private String status;
    private String content;
    private String adminId;
}
