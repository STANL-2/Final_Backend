package stanl_2.final_backend.domain.order.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderSelectIdDTO {
    private String orderId;
    private String title;
    private String content;
    private Boolean active;
    private String status;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private String contractId;
    private String adminId;
    private String memberId;
}
