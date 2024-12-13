package stanl_2.final_backend.domain.order.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderSelectSearchDTO {
    private String orderId;
    private String title;
    private String status;
    private String contractTitle;
    private String adminId;
    private String searchMemberId;
    private String memberId;
    private String adminName;
    private String memberName;
    private String productName;
    private String startDate;
    private String endDate;
    private String createdAt;
    private String centerId;
}
