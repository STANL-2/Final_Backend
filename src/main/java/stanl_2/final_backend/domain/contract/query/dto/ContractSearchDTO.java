package stanl_2.final_backend.domain.contract.query.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ContractSearchDTO {

    private String contractId;
    private String title;
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
    private String productId;
    private String startAt;
    private String endAt;
    private String carName;
    private Collection<? extends GrantedAuthority> roles;

    public ContractSearchDTO(String memberId, String searchMemberId, String centerId, String title, String startAt, String endAt,
                             String customerName, String customerClassifcation, String productId, String status,
                             String companyName, String customerPurchaseCondition, Collection<? extends GrantedAuthority> roles) {
        this.memberId = memberId;
        this.searchMemberId = searchMemberId;
        this.centerId = centerId;
        this.title = title;
        this.startAt = startAt;
        this.endAt = endAt;
        this.customerName = customerName;
        this.customerClassifcation = customerClassifcation;
        this.productId = productId;
        this.status = status;
        this.companyName = companyName;
        this.customerPurchaseCondition = customerPurchaseCondition;
        this.roles = roles;
    }
}
