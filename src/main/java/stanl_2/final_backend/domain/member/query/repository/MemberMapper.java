package stanl_2.final_backend.domain.member.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectAllDTO;
import stanl_2.final_backend.domain.member.query.dto.MemberDTO;
import stanl_2.final_backend.domain.member.query.dto.MemberExcelDTO;
import stanl_2.final_backend.domain.member.query.dto.MemberSearchDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface MemberMapper {
    MemberDTO findMemberInfoById(@Param("loginId") String loginId);

    String findNameById(@Param("memberId") String memberId);

    List<MemberDTO> findMembersByCenterId(@Param("centerId") String centerId);

    List<MemberDTO> findMembersByCenterList(@Param("centerList") List<String> centerList);

    List<MemberDTO> findMembersByOrganizationId(@Param("organizationId") String organizationId);

    MemberDTO findMemberInfoBymemberId(String memberId);

    List<MemberDTO> findMemberByName(String memberName);

    List<MemberSearchDTO> findMemberByConditions(Map<String, Object> params);

    Integer findMemberCnt(Map<String, Object> params);

    List<MemberExcelDTO> findMemberForExcel();
}
