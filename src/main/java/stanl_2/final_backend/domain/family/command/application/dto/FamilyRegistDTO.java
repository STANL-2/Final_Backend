package stanl_2.final_backend.domain.family.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FamilyRegistDTO {
    private String name;
    private String relation;
    private String birth;
    private String identNo;
    private String phone;
    private String sex;
    private Boolean disability;
    private Boolean die;
    private String note;
}
