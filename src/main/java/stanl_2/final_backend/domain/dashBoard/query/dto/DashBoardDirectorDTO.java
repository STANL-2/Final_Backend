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
public class DashBoardDirectorDTO {

    private Integer unreadPurchaseOrder;

    private List<String> centerList;

    private String memberLoginId;
    private String memberId;
}
