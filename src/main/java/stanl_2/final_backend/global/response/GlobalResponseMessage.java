package stanl_2.final_backend.global.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class GlobalResponseMessage {
    private int httpStatus;
    private String msg;
    private Object result;
}