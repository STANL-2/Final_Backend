package stanl_2.final_backend.domain.dashBoard.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DashBoardAdminDTO {

    private Integer unreadContract;
    private Integer unreadOrder;
    private Integer unreadPurchaseOrder;

    private String noticeTitle;
    private Integer totalPrice;

    private List<Map<String, String>> noticeList;

    private List<String> memberList;
    private String memberLoginId;
    private String memberId;
}
