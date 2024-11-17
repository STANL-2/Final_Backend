package stanl_2.final_backend.domain.member.command.application.service;

import stanl_2.final_backend.domain.member.command.application.dto.MemberDetailRegisterDTO;

import java.security.GeneralSecurityException;

public interface MemberCommandService {
    void registerMemberDetail(MemberDetailRegisterDTO memberDetailRegisterDTO) throws GeneralSecurityException;
}
