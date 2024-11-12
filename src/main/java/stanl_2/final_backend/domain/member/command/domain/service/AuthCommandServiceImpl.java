package stanl_2.final_backend.domain.member.command.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.member.command.application.service.AuthCommandService;
import stanl_2.final_backend.domain.member.command.domain.repository.AuthRepository;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service("commandAuthService")
public class AuthCommandServiceImpl implements AuthCommandService {

    private final AuthRepository authRepository;

    @Autowired
    public AuthCommandServiceImpl(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    private Timestamp getCurrentTimestamp() {
        ZonedDateTime nowKst = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        return Timestamp.from(nowKst.toInstant());
    }


}
