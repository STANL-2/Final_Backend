package stanl_2.final_backend.domain.member.aggregate.vo.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import stanl_2.final_backend.domain.member.aggregate.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SignUpRequestVO {

    @NotNull(message = "아이디를 입력해주세요.")
    private String loginId;

    @NotNull(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotNull(message = "이메일을 입력해 주세요.")
    @Email
    private String email;

    @NotNull(message = "성함을 입력해주세요.")
    private String name;

    @NotNull(message = "연락처를 입력해주세요.")
    private String phone;

    @NotNull(message = "역할을 입력해주세요.")
    private Role role;
}
