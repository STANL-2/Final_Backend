package stanl_2.final_backend.domain.member.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import stanl_2.final_backend.domain.member.query.dto.MemberDTO;
import stanl_2.final_backend.domain.member.query.dto.MemberDetailDTO;

@Mapper
public interface MemberMapper {
    MemberDTO findMemberInfoById(@Param("loginId") String loginId);

    MemberDetailDTO findMemberDetailById(@Param("loginId") String loginId);
}
