package stanl_2.final_backend.domain.member.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SigninResponseDTO {
    private String accessToken;
    private String refreshToken;
}
