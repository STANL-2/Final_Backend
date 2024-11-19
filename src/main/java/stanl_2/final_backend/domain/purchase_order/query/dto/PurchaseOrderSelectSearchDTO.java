package stanl_2.final_backend.domain.purchase_order.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PurchaseOrderSelectSearchDTO {
    private String PurchaseOrderId;
    private String title;
    private String status;
    private String orderId;
    private String adminName;
    private String memberName;
    private String productName;
    private String adminId;
    private String searchMemberId;
    private String startDate;
    private String endDate;
    private Collection<? extends GrantedAuthority> roles;

    public PurchaseOrderSelectSearchDTO(String title, String status, String adminId, String searchMemberId, String startDate, String endDate, Collection<? extends GrantedAuthority> roles) {
        this.title = title;
        this.status = status;
        this.adminId = adminId;
        this.searchMemberId = searchMemberId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.roles = roles;
    }
}
