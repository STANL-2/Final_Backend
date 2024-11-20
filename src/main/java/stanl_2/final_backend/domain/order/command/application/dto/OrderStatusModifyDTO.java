package stanl_2.final_backend.domain.order.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class OrderStatusModifyDTO {
    private String orderId;
    private String status;
    private String adminId;
}
