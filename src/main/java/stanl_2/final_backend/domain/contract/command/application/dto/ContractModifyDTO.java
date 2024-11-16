package stanl_2.final_backend.domain.contract.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ContractModifyDTO {

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
    private Integer remremainderPaymentPay;
    private Integer consignmentPayment;
    private String delveryDate;
    private String delveryLocation;
    private String status;
    private String NumberOfVehicles;
    private String createdUrl;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String memberId;
    private String centerId;
    private String customerId;
    private String productId;
}
