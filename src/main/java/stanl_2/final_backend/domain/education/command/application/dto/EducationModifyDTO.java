package stanl_2.final_backend.domain.education.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class EducationModifyDTO {
    private String graduationDate;
    private String note;
}