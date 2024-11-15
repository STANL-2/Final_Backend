package stanl_2.final_backend.domain.member.command.application.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class RefreshDTO {
    private String refreshToken;
    private String newAccessToken;
}
