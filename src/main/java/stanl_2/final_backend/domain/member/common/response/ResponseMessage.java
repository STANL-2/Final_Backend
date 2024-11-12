package stanl_2.final_backend.domain.member.common.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ResponseMessage {
    private int httpStatus;
    private String msg;
    private Object result;
}