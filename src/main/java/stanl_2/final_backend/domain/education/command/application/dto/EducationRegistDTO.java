package stanl_2.final_backend.domain.education.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EducationRegistDTO {
    private String entranceDate;
    private String graduationDate;
    private String name;
    private String major;
    private String score;
    private String note;
}
