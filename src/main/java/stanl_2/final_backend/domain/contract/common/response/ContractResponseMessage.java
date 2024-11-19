package stanl_2.final_backend.domain.contract.common.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ContractResponseMessage {
    private int httpStatus;
    private String msg;
    private Object result;
}