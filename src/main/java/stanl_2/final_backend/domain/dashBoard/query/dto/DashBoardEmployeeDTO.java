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
public class DashBoardEmployeeDTO {

    private Integer unreadContract;
    private Integer unreadOrder;

    private Integer totalPrice;

    private List<String> scheduleTitle;
    private List<Map<String, String>> noticeList;

    private List<String> memberList;
    private String memberLoginId;
    private String memberId;
}
