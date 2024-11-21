package stanl_2.final_backend.domain.contract.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContractSelectAllDTO {

    private String contractId;
    private String title;
    private String customerName;
    private String companyName;
    private String status;
    private String createdUrl;
    private String updatedUrl;
    private boolean active;
    private String memberId;
    private String centerId;
    private String customerId;
    private String productId;
    private String carName;
}
