package stanl_2.final_backend.domain.contract.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ContractSelectAllDTO {

    private String id;
    private String name;
    private String custName;
    private String compName;
    private String status;
    private String createdUrl;
    private String updatedUrl;
    private boolean active;
    private String memId;
    private String centId;
    private String custId;
    private String prodId;
    private String prodName;
}
