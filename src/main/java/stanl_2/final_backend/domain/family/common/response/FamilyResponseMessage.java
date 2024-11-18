package stanl_2.final_backend.domain.family.common.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FamilyResponseMessage {
    private int httpStatus;
    private String msg;
    private Object result;
}