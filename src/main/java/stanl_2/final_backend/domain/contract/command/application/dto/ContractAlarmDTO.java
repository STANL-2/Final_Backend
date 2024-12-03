package stanl_2.final_backend.domain.contract.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ContractAlarmDTO {

    private String contractId;
    private String customerName;
    private String memberId;
    private String adminId;
}
