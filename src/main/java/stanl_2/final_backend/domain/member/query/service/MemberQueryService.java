package stanl_2.final_backend.domain.member.query.service;

import stanl_2.final_backend.domain.member.query.dto.MemberDTO;

import java.security.GeneralSecurityException;

public interface MemberQueryService {
    MemberDTO selectMemberInfo(String name) throws GeneralSecurityException;
}
