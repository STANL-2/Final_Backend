package stanl_2.final_backend.domain.member.query.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberRoleMapper {
    List<String> findMembersbyRole(String role);

    String findMembersRolebyId(String memberId);
}
