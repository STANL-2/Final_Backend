package stanl_2.final_backend.domain.member.command.application.service;

import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;
import stanl_2.final_backend.domain.member.command.application.dto.*;

import java.security.GeneralSecurityException;

public interface AuthCommandService {
    void signup(SignupDTO signupDTO, MultipartFile imageUrl) throws GeneralSecurityException;

    RefreshDTO refreshAccessToken(String refreshToken);

    void grantAuthority(GrantDTO grantDTO);

    SigninResponseDTO signin(SigninRequestDTO signinRequestDTO) throws GeneralSecurityException;

    void sendEmail(CheckMailDTO checkMailDTO) throws GeneralSecurityException, MessagingException;

    void checkNum(CheckNumDTO checkNumDTO) throws GeneralSecurityException;

    void sendNewPwd(String loginId) throws MessagingException, GeneralSecurityException;
}
