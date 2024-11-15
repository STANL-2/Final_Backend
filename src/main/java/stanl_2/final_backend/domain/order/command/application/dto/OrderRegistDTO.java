package stanl_2.final_backend.domain.order.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class OrderRegistDTO {
    private String title;
    private String content;
    private String conrId;
    private String memId;
}
