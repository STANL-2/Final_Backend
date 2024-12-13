package stanl_2.final_backend.domain.education.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EducationDTO {
    private String entranceDate;
    private String graduationDate;
    private String name;
    private String major;
    private String score;
    private String note;
}
