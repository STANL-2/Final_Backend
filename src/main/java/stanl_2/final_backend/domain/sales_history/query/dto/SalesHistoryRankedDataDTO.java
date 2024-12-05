package stanl_2.final_backend.domain.sales_history.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalesHistoryRankedDataDTO {
    private String memberId;
    private BigDecimal totalIncentive;
    private BigDecimal totalPerformance;
    private BigDecimal totalSales;
    private String centerId;

    private List<String> memberList;
    private List<String> centerList;
    private String startDate;
    private String endDate;
    private String createdAt;
    private String month;
    private String year;
    private String orderBy;
    private String groupBy;
    private String period;
}
