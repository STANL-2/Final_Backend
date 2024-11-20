package stanl_2.final_backend.domain.member.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.member.common.exception.MemberCommonException;
import stanl_2.final_backend.domain.member.common.exception.MemberErrorCode;
import stanl_2.final_backend.domain.member.query.dto.MemberDTO;
import stanl_2.final_backend.domain.member.query.repository.MemberMapper;
import stanl_2.final_backend.domain.member.query.repository.MemberRoleMapper;
import stanl_2.final_backend.global.utils.AESUtils;

import java.security.GeneralSecurityException;
import java.util.List;

@Service(value = "queryMemberService")
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberMapper memberMapper;
    private final AESUtils aesUtils;
    private final MemberRoleMapper memberRoleMapper;
//    private final AlarmService alarmService;

    @Autowired
    public MemberQueryServiceImpl(MemberMapper memberMapper, AESUtils aesUtils,
                                  MemberRoleMapper memberRoleMapper
//            , AlarmService alarmService
    ) {
        this.memberMapper = memberMapper;
        this.aesUtils = aesUtils;
        this.memberRoleMapper = memberRoleMapper;
//        this.alarmService = alarmService;
    }

    @Override
    @Transactional(readOnly = true)
    public MemberDTO selectMemberInfo(String name) throws GeneralSecurityException {

        MemberDTO memberInfo = memberMapper.findMemberInfoById(name);

        if(memberInfo == null){
            throw new MemberCommonException(MemberErrorCode.MEMBER_NOT_FOUND);
        }

        memberInfo.setName(aesUtils.decrypt(memberInfo.getName()));
        memberInfo.setEmail(aesUtils.decrypt(memberInfo.getEmail()));
        memberInfo.setIdentNo(aesUtils.decrypt(memberInfo.getIdentNo()));
        memberInfo.setPhone(aesUtils.decrypt(memberInfo.getPhone()));
        memberInfo.setEmergePhone(aesUtils.decrypt(memberInfo.getEmergePhone()));
        memberInfo.setAddress(aesUtils.decrypt(memberInfo.getAddress()));
        memberInfo.setBankName(aesUtils.decrypt(memberInfo.getBankName()));
        memberInfo.setAccount(aesUtils.decrypt(memberInfo.getAccount()));
        memberInfo.setCenterId(memberInfo.getCenterId());

        return memberInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> selectMemberByRole(String role){

        List<String> memberList = memberRoleMapper.findMembersbyRole(role);

        return memberList;
    }
}
