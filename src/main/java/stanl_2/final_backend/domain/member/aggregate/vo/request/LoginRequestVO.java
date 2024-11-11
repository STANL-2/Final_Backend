package stanl_2.final_backend.domain.member.aggregate.vo.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LoginRequestVO {

    @NotNull(message = "아이디를 입력해주세요.")
    private String loginId;

    @NotNull(message = "비밀번호를 입력해주세요.")
    private String password;
}
