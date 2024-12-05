package stanl_2.final_backend.domain.sales_history.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalesHistoryStatisticsDTO {
    private int incentive;
    private int performance;
    private int totalSales;
    private String orderBy;
    private String month;
    private String year;
    private String createdAt;
}
