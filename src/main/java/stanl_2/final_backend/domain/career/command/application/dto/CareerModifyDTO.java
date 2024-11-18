package stanl_2.final_backend.domain.career.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CareerModifyDTO {
    private String memberLoginId;
    private String resignDate;
    private String note;
    private String memId;
}
