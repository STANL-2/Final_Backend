package stanl_2.final_backend.domain.member.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GrantDTO {
    private String memberId;
    private String loginId;
    private String role;
}
