package stanl_2.final_backend.domain.member.query.service;

import java.security.GeneralSecurityException;

public interface AuthQueryService {
    String selectMemberIdByLoginId(String name);

    String findEmail(String loginId) throws GeneralSecurityException;
}
