package stanl_2.final_backend.domain.member.command.application.service;

import stanl_2.final_backend.domain.member.command.application.dto.GrantDTO;
import stanl_2.final_backend.domain.member.command.application.dto.SigninRequestDTO;
import stanl_2.final_backend.domain.member.command.application.dto.SigninResponseDTO;
import stanl_2.final_backend.domain.member.command.application.dto.SignupDTO;

public interface AuthCommandService {
    void signup(SignupDTO signupDTO);

    SigninResponseDTO signin(SigninRequestDTO signinRequestDTO);

    void grantAuthority(GrantDTO grantDTO);
}
