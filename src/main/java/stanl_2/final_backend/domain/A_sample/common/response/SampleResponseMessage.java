package stanl_2.final_backend.domain.A_sample.common.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SampleResponseMessage {
    private int httpStatus;
    private String msg;
    private Object result;
}