package stanl_2.final_backend.domain.contract.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ContractSearchDTO {

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
    private String delveryLocationLoc;
    private String status;
    private String numberOfVehicles;
    private String createdUrl;
    private String updatedUrl;
    private boolean active;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private String memberId;
    private String memId;
    private String centerId;
    private String customerId;
    private String productId;
    private String startAt;
    private String endAt;
    private String productName;

    public ContractSearchDTO(String memberId, String memId, String centerId, String name, String startAt, String endAt, String customerName, String customerClassifcation, String productId, String status, String companyName, String customerPurchaseCondition) {
        this.memberId = memberId;
        this.memId = memId;
        this.centerId = centerId;
        this.name = name;
        this.startAt = startAt;
        this.endAt = endAt;
        this.customerName = customerName;
        this.customerClassifcation = customerClassifcation;
        this.productId = productId;
        this.status = status;
        this.companyName = companyName;
        this.customerPurchaseCondition = customerPurchaseCondition;
    }
}
