package stanl_2.final_backend.domain.promotion.common.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PromotionResponseMessage {
    private int httpStatus;
    private String msg;
    private Object result;
}
