package stanl_2.final_backend.domain.purchase_order.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PurchaseOrderSelectIdDTO {
    private String PurchaseOrderId;
    private String title;
    private String content;
    private Boolean active;
    private String status;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private String orderId;
    private String adminId;
    private String memberId;
}
