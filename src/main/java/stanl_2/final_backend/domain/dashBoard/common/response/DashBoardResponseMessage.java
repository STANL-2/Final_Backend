package stanl_2.final_backend.domain.dashBoard.common.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class DashBoardResponseMessage {
    private int httpStatus;
    private String msg;
    private Object result;
}