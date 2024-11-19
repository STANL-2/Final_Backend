package stanl_2.final_backend.domain.member.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.member.common.exception.MemberCommonException;
import stanl_2.final_backend.domain.member.common.exception.MemberErrorCode;
import stanl_2.final_backend.domain.member.query.repository.AuthMapper;

@Slf4j
@Service(value = "queryAuthService")
public class AuthQueryServiceImpl implements AuthQueryService {

    private final AuthMapper authMapper;

    @Autowired
    public AuthQueryServiceImpl(AuthMapper authMapper) {
        this.authMapper = authMapper;
    }

    @Override
    @Transactional
    public String selectMemberIdByLoginId(String loginId){

        String id = authMapper.selectIdByMemberName(loginId);

        if(id == null){
            throw new MemberCommonException(MemberErrorCode.MEMBER_ID_NOT_FOUND);
        }

        return id;
    }
}
