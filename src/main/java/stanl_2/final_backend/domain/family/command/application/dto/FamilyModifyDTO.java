package stanl_2.final_backend.domain.family.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FamilyModifyDTO {
    private String relation;
    private String phone;
    private Boolean disability;
    private Boolean die;
    private String note;
}
