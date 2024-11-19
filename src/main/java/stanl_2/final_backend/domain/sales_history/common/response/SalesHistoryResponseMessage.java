package stanl_2.final_backend.domain.sales_history.common.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SalesHistoryResponseMessage {
    private int httpStatus;
    private String msg;
    private Object result;
}