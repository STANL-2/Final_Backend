package stanl_2.final_backend.domain.contract.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ContractSeletIdDTO {

    private String contractId;
    private String name;
    private String customerName;
    private String customerIdentifiNo;
    private String customerAddrress;
    private String customerEmail;
    private String customerPhone;
    private String companyName;
    private String customerClassifcation;
    private String customerPurchaseCondition;
    private String serialNum;
    private String selectOption;
    private Integer downPayment;
    private Integer intermediatePayment;
    private Integer remainderPayment;
    private Integer consignmentPayment;
    private String delveryDate;
    private String delveryLocation;
    private String status;
    private String NumberOfVehicles;
    private String createdUrl;
    private String updatedUrl;
    private boolean active;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private String memberId;
    private String centerId;
    private String customerId;
    private String productId;
}
