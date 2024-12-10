package stanl_2.final_backend.domain.dashBoard.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DashBoardDirectorDTO {

    private Integer totalContract;
    private Integer totalOrder;
    private Integer totalPurchaseOrder;

    private BigDecimal totalPrice;

    private List<String> centerList;
    private List<Map<String, String>> noticeList;

    private String memberLoginId;
    private String memberId;
}
