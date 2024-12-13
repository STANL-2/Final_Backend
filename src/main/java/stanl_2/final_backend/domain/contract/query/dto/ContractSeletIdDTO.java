package stanl_2.final_backend.domain.contract.query.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContractSeletIdDTO {

    private String contractId;
    private String title;
    private String customerName;
    private String customerSex;
    private String customerIdentifiNo;
    private Integer customerAge;
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
    private String deliveryLocation;
    private String status;
    private Integer numberOfVehicles;
    private Integer totalSales;
    private String carName;
    private String createdUrl;
    private String updatedUrl;
    private String vehiclePrice;
    private boolean active;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private String memberId;
    private String centerId;
    private String customerId;
    private String productId;
    private String productName;
}
