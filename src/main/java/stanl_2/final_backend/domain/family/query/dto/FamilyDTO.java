package stanl_2.final_backend.domain.family.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FamilyDTO {
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
