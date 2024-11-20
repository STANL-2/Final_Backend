package stanl_2.final_backend.domain.sales_history.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SalesHistoryStatisticsDTO {
    private int incentive;
    private int performance;
    private int totalSales;
}
