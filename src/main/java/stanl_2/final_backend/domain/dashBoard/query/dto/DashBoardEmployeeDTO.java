package stanl_2.final_backend.domain.dashBoard.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DashBoardEmployeeDTO {

    private Integer unreadContract;
    private Integer unreadOrder;
    private Integer unreadPurchaseOrder;

    private String noticeTitle;

    private String memberLoginId;
    private String memberId;
}
