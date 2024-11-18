package stanl_2.final_backend.domain.contract.command.application.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
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
    private Integer remainderPayment;
    private Integer consignmentPayment;
    private String delveryDate;
    private String delveryLocation;
    private String status;
    private String numberOfVehicles;
    private String createdUrl;
    private Boolean active;
    private String createdAt;
    private String updatedAt;
    private String memberId;
    private String centerId;
    private String customerId;
    private String productId;
    private Collection<? extends GrantedAuthority> roles;
}
