package stanl_2.final_backend.domain.organization.common.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class OrganizationResponseMessage {
    private int httpStatus;
    private String msg;
    private Object result;
}