package stanl_2.final_backend.domain.member.query.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectAllDTO;
import stanl_2.final_backend.domain.center.query.service.CenterQueryService;
import stanl_2.final_backend.domain.member.common.exception.MemberCommonException;
import stanl_2.final_backend.domain.member.common.exception.MemberErrorCode;
import stanl_2.final_backend.domain.member.query.dto.MemberDTO;
import stanl_2.final_backend.domain.member.query.dto.MemberSearchDTO;
import stanl_2.final_backend.domain.member.query.repository.MemberMapper;
import stanl_2.final_backend.domain.member.query.repository.MemberRoleMapper;
import stanl_2.final_backend.global.utils.AESUtils;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public MemberDTO selectMemberInfoById(String memberId) throws GeneralSecurityException {

        MemberDTO memberDTO = memberMapper.findMemberInfoBymemberId(memberId);

        memberDTO.setName(aesUtils.decrypt(memberDTO.getName()));
        memberDTO.setImageUrl(aesUtils.decrypt(memberDTO.getImageUrl()));

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

    @Override
    @Transactional(readOnly = true)
    public Page<MemberSearchDTO> selectMemberBySearch(Pageable pageable, MemberSearchDTO memberSearchDTO) throws GeneralSecurityException {
        int offset = Math.toIntExact(pageable.getOffset());
        int size = pageable.getPageSize();

        // 정렬 정보 가져오기
        Sort sort = pageable.getSort();
        String sortField = null;
        String sortOrder = null;
        if (sort.isSorted()) {
            sortField = sort.iterator().next().getProperty();
            sortOrder = sort.iterator().next().isAscending() ? "ASC" : "DESC";
        }

        Map<String, Object> params = new HashMap<>();
        params.put("offset", offset);
        params.put("size", size);
        params.put("sortField", sortField);
        params.put("sortOrder", sortOrder);

        params.put("loginId", memberSearchDTO.getLoginId());
        params.put("memberName", memberSearchDTO.getMemberName());
        params.put("phone", memberSearchDTO.getPhone());
        params.put("email", memberSearchDTO.getEmail());
        params.put("centerName", memberSearchDTO.getCenterName());
        params.put("organizationName", memberSearchDTO.getOrganizationName());

        List<MemberSearchDTO> memberList = memberMapper.findMemberByConditions(params);

        Integer count = memberMapper.findMemberCnt(params);

        // 암호화 풀기
        for(int i=0;i<memberList.size();i++){
            memberList.get(i).setMemberName(aesUtils.decrypt(memberList.get(i).getMemberName()));
            memberList.get(i).setPhone(aesUtils.decrypt(memberList.get(i).getPhone()));
            memberList.get(i).setEmail(aesUtils.decrypt(memberList.get(i).getEmail()));
        }

        return new PageImpl<>(memberList, pageable, count);
    }
}
