package stanl_2.final_backend.domain.member.query.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthMapper {
    String selectIdByMemberName(@Param("loginId") String loginId);

    String findEmailByLoginId(@Param("loginId") String loginId);
}
