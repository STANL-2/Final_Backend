package stanl_2.final_backend.domain.member.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectAllDTO;
import stanl_2.final_backend.domain.center.query.service.CenterQueryService;
import stanl_2.final_backend.domain.member.common.exception.MemberCommonException;
import stanl_2.final_backend.domain.member.common.exception.MemberErrorCode;
import stanl_2.final_backend.domain.member.query.dto.MemberDTO;
import stanl_2.final_backend.domain.member.query.repository.MemberMapper;
import stanl_2.final_backend.domain.member.query.repository.MemberRoleMapper;
import stanl_2.final_backend.global.utils.AESUtils;

import java.security.GeneralSecurityException;
import java.util.List;

@Slf4j
@Service(value = "queryMemberService")
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberMapper memberMapper;
    private final CenterQueryService centerQueryService;
    private final AESUtils aesUtils;
    private final MemberRoleMapper memberRoleMapper;

    @Autowired
    public MemberQueryServiceImpl(MemberMapper memberMapper, CenterQueryService centerQueryService, AESUtils aesUtils,
                                  MemberRoleMapper memberRoleMapper) {
        this.memberMapper = memberMapper;
        this.centerQueryService = centerQueryService;
        this.aesUtils = aesUtils;
        this.memberRoleMapper = memberRoleMapper;
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
        memberInfo.setImageUrl(aesUtils.decrypt(memberInfo.getImageUrl()));
        memberInfo.setCenterId(memberInfo.getCenterId());

        return memberInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> selectMemberByRole(String role){

        List<String> memberList = memberRoleMapper.findMembersbyRole(role);

        return memberList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemberDTO> selectMemberByCenterId(String centerId){

        List<MemberDTO> memberList = memberMapper.findMembersByCenterId(centerId);

        memberList.forEach(dto -> {
            try {
                dto.setName(selectNameById(dto.getMemberId()));

                dto.setEmail(aesUtils.decrypt(dto.getEmail()));
            } catch (Exception e) {
                throw new MemberCommonException(MemberErrorCode.MEMBER_NOT_FOUND);
            }
        });

        return memberList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemberDTO> selectMemberByCenterList(List<String> centerList) {
        List<MemberDTO> memberList = memberMapper.findMembersByCenterList(centerList);

        memberList.forEach(dto -> {
            try {
                dto.setName(selectNameById(dto.getMemberId()));
            } catch (Exception e) {
                throw new MemberCommonException(MemberErrorCode.MEMBER_NOT_FOUND);
            }
        });

        return memberList;
    }

    @Override
    @Transactional(readOnly = true)
    public String selectNameById(String memberId) throws GeneralSecurityException {

        String name = memberMapper.findNameById(memberId);
        name = aesUtils.decrypt(name);

        return name;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemberDTO> selectMemberByOrganizationId(String organizationId) throws GeneralSecurityException {

        List<MemberDTO> memberList = memberMapper.findMembersByOrganizationId(organizationId);

        for(int i=0;i<memberList.size();i++){
            memberList.get(i).getName();
            MemberDTO member = memberList.get(i);
            log.info(member.getName());
            member.setName(aesUtils.decrypt(member.getName()));
            memberList.set(i, member);
        }

        return memberList;
    }

    @Override
    @Transactional(readOnly = true)
    public MemberDTO selectMemberInfoById(String memberId) {

        MemberDTO memberDTO = memberMapper.findMemberInfoBymemberId(memberId);

        return memberDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemberDTO> selectMemberByName(String name) throws GeneralSecurityException {

        String memberName = aesUtils.encrypt(name);
        List<MemberDTO> memberList = memberMapper.findMemberByName(memberName);

        if(memberList == null){
            throw new MemberCommonException(MemberErrorCode.MEMBER_NOT_FOUND);
        }

        for(int i=0;i<memberList.size();i++){
            memberList.get(i).getName();
            MemberDTO member = memberList.get(i);
            member.setName(aesUtils.decrypt(member.getName()));

            if (member.getCenterId() != null) {
                String centerName = centerQueryService.selectNameById(member.getCenterId());
                member.setCenterName(centerName);
            }

            memberList.set(i, member);
        }
        return memberList;
    }
}
