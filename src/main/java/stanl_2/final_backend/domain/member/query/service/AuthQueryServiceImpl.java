package stanl_2.final_backend.domain.member.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.member.command.application.dto.CheckMailDTO;
import stanl_2.final_backend.domain.member.common.exception.MemberCommonException;
import stanl_2.final_backend.domain.member.common.exception.MemberErrorCode;
import stanl_2.final_backend.domain.member.query.repository.AuthMapper;
import stanl_2.final_backend.global.utils.AESUtils;

import java.security.GeneralSecurityException;

@Slf4j
@Service(value = "queryAuthService")
public class AuthQueryServiceImpl implements AuthQueryService {

    private final AuthMapper authMapper;
    private final AESUtils aesUtils;

    @Autowired
    public AuthQueryServiceImpl(AuthMapper authMapper,
                                AESUtils aesUtils) {
        this.authMapper = authMapper;
        this.aesUtils = aesUtils;
    }

    @Override
    public String selectMemberIdByLoginId(String loginId){

        String id = authMapper.selectIdByMemberName(loginId);

        if(id == null){
            throw new MemberCommonException(MemberErrorCode.MEMBER_ID_NOT_FOUND);
        }

        return id;
    }

    @Override
    @Transactional(readOnly = true)
    public String findEmail(String loginId) throws GeneralSecurityException {

        String email = authMapper.findEmailByLoginId(loginId);

        email = aesUtils.decrypt(email);

        return email;
    }
}
