package stanl_2.final_backend.domain.member.command.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.member.command.application.dto.SignupDTO;
import stanl_2.final_backend.domain.member.command.application.service.AuthCommandService;
import stanl_2.final_backend.domain.member.command.domain.repository.AuthRepository;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service("commandAuthService")
public class AuthCommandServiceImpl implements AuthCommandService {

    private final AuthRepository authRepository;

    @Autowired
    public AuthCommandServiceImpl(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    private String getCurrentTimestamp() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return nowKst.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    @Transactional
    public void signup(SignupDTO signupDTO) {

    }
}
