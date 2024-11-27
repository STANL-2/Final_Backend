package stanl_2.final_backend.domain.sales_history.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalesHistoryRegistDTO {
    private Integer numberOfVehicles;
    private Integer totalSales;
    private Double incentive;
    private String contractId;
    private String customerInfoId;
    private String productId;
    private String memberId;
    private String centerId;
}
