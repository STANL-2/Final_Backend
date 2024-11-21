package stanl_2.final_backend.domain.order.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderSelectAllDTO {
    private String orderId;
    private String title;
    private String status;
    private String contractTitle;
    private String adminName;
    private String memberName;
    private String productName;
}
