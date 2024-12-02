package stanl_2.final_backend.domain.purchase_order.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PurchaseOrderModifyDTO {
    private String purchaseOrderId;
    private String title;
    private String content;
    private String memberId;
}
