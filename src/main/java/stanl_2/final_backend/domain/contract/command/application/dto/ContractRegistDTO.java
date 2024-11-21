package stanl_2.final_backend.domain.contract.command.application.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ContractRegistDTO {

    private String title;
    private String customerName;
    private String customerSex;
    private String customerIdentifiNo;
    private Integer customerAge;
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
    private Integer totalSales;
    private String carName;
    private String delveryDate;
    private String delveryLocation;
    private String status;
    private String numberOfVehicles;
    private String createdUrl;
    private String memberId;
}
