package stanl_2.final_backend.domain.career.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CareerRegistDTO {
    private String emplDate;
    private String resignDate;
    private String name;
    private String note;
    private String memberId;
}
