package stanl_2.final_backend.domain.sales_history.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalesHistorySelectDTO {
    private String salesHistoryId;
    private Integer salesHistoryNumberOfVehicles;
    private Integer salesHistoryTotalSales;
    private Integer salesHistoryIncentive;
    private String createdAt;
    private String deletedAt;
    private String active;
    private String contractId;
    private String customerId;
    private String productId;
    private String memberId;
    private String centerId;
    private String searcherName;
    private String orderBy;

}
