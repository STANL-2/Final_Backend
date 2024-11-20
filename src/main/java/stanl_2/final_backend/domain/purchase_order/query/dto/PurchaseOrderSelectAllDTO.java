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
public class PurchaseOrderSelectAllDTO {
    private String PurchaseOrderId;
    private String title;
    private String status;
    private String orderId;
    private String adminName;
    private String memberName;
    private String productName;
    private String memberId;
    private Collection<? extends GrantedAuthority> roles;
}