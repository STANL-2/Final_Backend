package stanl_2.final_backend.domain.contract.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ContractStatusModifyDTO {
    private String contractId;
    private String status;
    private String adminId;
}
