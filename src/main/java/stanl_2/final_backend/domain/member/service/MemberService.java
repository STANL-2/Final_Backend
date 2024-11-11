package stanl_2.final_backend.domain.member.service;

import org.springframework.security.core.Authentication;
import stanl_2.final_backend.domain.member.aggregate.dto.SignUpMemberDTO;

public interface MemberService {
    Boolean signUp(SignUpMemberDTO memberRequestDTO);

    String signin(Authentication authenticationResponse);
}
