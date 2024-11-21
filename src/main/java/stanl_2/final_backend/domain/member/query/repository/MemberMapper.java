package stanl_2.final_backend.domain.member.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stanl_2.final_backend.domain.center.query.dto.CenterSelectAllDTO;
import stanl_2.final_backend.domain.member.query.dto.MemberDTO;

import java.util.List;

@Mapper
public interface MemberMapper {
    MemberDTO findMemberInfoById(@Param("loginId") String loginId);

    String findNameById(@Param("memberId") String memberId);

    List<MemberDTO> findMembersByCenterId(@Param("centerId") String centerId);

    List<MemberDTO> findMembersByCenterList(@Param("centerList") List<CenterSelectAllDTO> centerList);
}
