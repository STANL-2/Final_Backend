package stanl_2.final_backend.domain.contract.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContractSearchDTO {

    private String contractId;
    private String title;
    private String customerName;
    private String customerIdentifiNo;
    private String customerAddress;
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
    private String deliveryDate;
    private String deliveryLocationLoc;
    private String status;
    private String numberOfVehicles;
    private String totalSales;
    private String createdUrl;
    private String updatedUrl;
    private boolean active;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private String memberId;
    private String searchMemberId;
    private String centerId;
    private String customerId;
    private String startDate;
    private String endDate;
    private String carName;
    private String productName;
}
