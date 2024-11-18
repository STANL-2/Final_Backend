package stanl_2.final_backend.domain.career.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CareerDTO {
    private String emplDate;
    private String resignDate;
    private String name;
    private String note;
}
