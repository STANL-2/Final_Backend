package stanl_2.final_backend.domain.order.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderSelectSearchDTO {
    private String orderId;
    private String title;
    private String status;
    private String contractName;
    private String adminId;
    private String memberId;
    private String memId;
    private String adminName;
    private String memberName;
    private String productName;
    private String startDate;
    private String endDate;

    public OrderSelectSearchDTO(String title, String status, String adminId, String memberId, String memId, String startDate, String endDate) {
        this.title = title;
        this.status = status;
        this.adminId = adminId;
        this.memId = memId;
        this.memberId = memberId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
