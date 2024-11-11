package stanl_2.final_backend.domain.member.aggregate.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginMemberDTO {
    private String loginId;
    private String password;
}
