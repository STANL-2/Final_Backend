package stanl_2.final_backend.domain.career.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CareerRegistDTO {
    private String memberLoginId;
    private String emplDate;
    private String resignDate;
    private String name;
    private String note;
    private String memId;
}
