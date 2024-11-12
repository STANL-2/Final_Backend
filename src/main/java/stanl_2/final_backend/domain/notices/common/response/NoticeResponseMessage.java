package stanl_2.final_backend.domain.notices.common.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NoticeResponseMessage {
    private int httpStatus;
    private String msg;
    private Object result;
}
