package stanl_2.final_backend.domain.customer.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerContractDTO {
    private String contractId;
    private String centerName;
    private String contractCarName;
    private String contractTTL;
    private String contractTotalSale;
    private String contractState;
}
