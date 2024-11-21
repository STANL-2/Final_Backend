package stanl_2.final_backend.domain.contract.command.application.dto;

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
public class ContractDeleteDTO {
    private String contractId;
    private String memberId;
    private Collection<? extends GrantedAuthority> roles;
}
