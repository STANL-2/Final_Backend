package stanl_2.final_backend.domain.order.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderModifyDTO {
    private String orderId;
    private String title;
    private String content;
    private String memberId;
}
