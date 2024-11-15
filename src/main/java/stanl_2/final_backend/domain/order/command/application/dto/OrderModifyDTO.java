package stanl_2.final_backend.domain.order.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class OrderModifyDTO {
    private String id;
    private String title;
    private String content;
    private String memId;
}
