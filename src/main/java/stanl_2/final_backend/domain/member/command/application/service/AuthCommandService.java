package stanl_2.final_backend.domain.member.command.application.service;

import stanl_2.final_backend.domain.member.command.application.dto.SignupDTO;

public interface AuthCommandService {
    void signup(SignupDTO signupDTO);
}
