package stanl_2.final_backend.domain.dashBoard.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DashBoardAdminDTO {

    private String unreadContract;
    private String unreadPurchase;
    private String unreadPurchaseOrder;

    private String noticeTitle;

    private String memberLoginId;
    private String memberId;
}
