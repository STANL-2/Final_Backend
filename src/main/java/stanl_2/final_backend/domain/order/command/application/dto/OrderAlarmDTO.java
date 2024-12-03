package stanl_2.final_backend.domain.order.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderAlarmDTO {

    private String orderId;
    private String title;
    private String memberId;
    private String adminId;
}
