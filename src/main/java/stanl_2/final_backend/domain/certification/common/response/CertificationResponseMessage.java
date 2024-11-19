package stanl_2.final_backend.domain.certification.common.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CertificationResponseMessage {
    private int httpStatus;
    private String msg;
    private Object result;
}