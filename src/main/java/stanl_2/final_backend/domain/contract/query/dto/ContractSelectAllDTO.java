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
    private String memberId;
    private String centerId;
    private String customerId;
    private String productId;
    private String carName;
    private String createdAt;
    private String customerClassifcation;
    private String customerPurchaseCondition;
    private String customerIdentifiNo;
    private String customerAddress;
    private String customerEmail;
    private String customerPhone;
    private String serialNum;
    private String selectOption;
    private Integer downPayment;
    private Integer intermediatePayment;
    private Integer remainderPayment;
    private Integer consignmentPayment;
    private String deliveryDate;
    private String deliveryLocationLoc;
    private String numberOfVehicles;
    private String totalSales;
    private String createdUrl;
    private String updatedUrl;
    private boolean active;
    private String updatedAt;
    private String deletedAt;
    private String searchMemberId;
    private String startDate;
    private String endDate;
    private String productName;
}
