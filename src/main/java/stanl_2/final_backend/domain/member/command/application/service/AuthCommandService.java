package stanl_2.final_backend.domain.member.command.application.service;

import stanl_2.final_backend.domain.member.command.application.dto.*;

public interface AuthCommandService {
    void signup(SignupDTO signupDTO);

    RefreshDTO refreshAccessToken(String refreshToken);

    void grantAuthority(GrantDTO grantDTO);

    SigninResponseDTO signin(SigninRequestDTO signinRequestDTO);
}
