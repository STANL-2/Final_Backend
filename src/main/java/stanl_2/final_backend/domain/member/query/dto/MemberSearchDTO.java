package stanl_2.final_backend.domain.member.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MemberSearchDTO {
    private String loginId;
    private String memberName;
    private String phone;
    private String email;
    private String centerName;
    private String organizationName;
}
