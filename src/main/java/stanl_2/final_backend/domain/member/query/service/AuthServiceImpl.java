package stanl_2.final_backend.domain.member.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stanl_2.final_backend.domain.member.query.repository.AuthMapper;

@Slf4j
@Service(value = "queryAuthService")
public class AuthServiceImpl implements AuthService{

    private final AuthMapper authMapper;

    @Autowired
    public AuthServiceImpl(AuthMapper authMapper) {
        this.authMapper = authMapper;
    }
}
