package stanl_2.final_backend.domain.member.query.service;

import stanl_2.final_backend.domain.member.command.application.dto.CheckMailDTO;

import java.security.GeneralSecurityException;

public interface AuthQueryService {
    String selectMemberIdByLoginId(String name);

    String findEmail(CheckMailDTO checkMailDTO) throws GeneralSecurityException;
}
