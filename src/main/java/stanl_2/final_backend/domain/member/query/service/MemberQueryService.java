package stanl_2.final_backend.domain.member.query.service;

import stanl_2.final_backend.domain.member.query.dto.MemberDTO;

import java.security.GeneralSecurityException;
import java.util.List;

public interface MemberQueryService {
    MemberDTO selectMemberInfo(String name) throws GeneralSecurityException;

    List<String> selectMemberByRole(String role);

    List<MemberDTO> selectMemberByCenterId(String centerId);
    List<MemberDTO> selectMemberByCenterList(List<String> centerList);

    String selectNameById(String memberId) throws GeneralSecurityException;

    List<MemberDTO> selectMemberByOrganizationId(String organizationId) throws GeneralSecurityException;

    MemberDTO selectMemberInfoById(String memberId);

    List<MemberDTO> selectMemberByName(String name) throws GeneralSecurityException;

//    MemberDetailDTO selectMemberDetail(String name) throws GeneralSecurityException;
}
