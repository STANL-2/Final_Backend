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
public class PurchaseOrderSelectIdDTO {
    private String PurchaseOrderId;
    private String title;
    private String content;
    private Boolean active;
    private String status;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private String OrderId;
    private String adminId;
    private String memberId;
    private Collection<? extends GrantedAuthority> roles;
}
