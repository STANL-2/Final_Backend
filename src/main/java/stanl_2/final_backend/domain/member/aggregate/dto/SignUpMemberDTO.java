package stanl_2.final_backend.domain.member.aggregate.dto;

import lombok.*;
import stanl_2.final_backend.domain.member.aggregate.Role;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignUpMemberDTO {
    private String loginId;
    private String password;
    private String email;
    private String name;
    private String phone;
    private Role role;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
