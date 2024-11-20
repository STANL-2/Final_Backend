package stanl_2.final_backend.domain.member.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stanl_2.final_backend.domain.member.query.dto.MemberDTO;

@Mapper
public interface MemberMapper {
    MemberDTO findMemberInfoById(@Param("loginId") String loginId);

    String findNameById(@Param("memberId") String memberId);
}
