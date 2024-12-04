package stanl_2.final_backend.domain.purchase_order.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PurchaseOrderAlarmDTO {

    private String purchaseOrderId;
    private String title;
    private String memberId;
    private String adminId;
}
