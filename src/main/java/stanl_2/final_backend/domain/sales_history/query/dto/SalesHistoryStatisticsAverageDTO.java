package stanl_2.final_backend.domain.sales_history.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalesHistoryStatisticsAverageDTO {
    private BigDecimal averageTotalSales;
    private BigDecimal averageTotalIncentive;
    private BigDecimal averageTotalPerformance;
    private String month;
    private String year;
    private String period;
}
