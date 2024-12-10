package stanl_2.final_backend.domain.sales_history.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalesHistorySearchDTO {
    private String searcherName;
    private String startDate;
    private String endDate;
    private List<String> memberList;
    private List<String> centerList;
    private String orderBy;
    private String customerName;
    private List<String> customerList;
    private List<String> productList;
    private List<String> contractList;
    private String contractId;
    private String productId;
    private String period;
}
