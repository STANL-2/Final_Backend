package stanl_2.final_backend.domain.member.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanl_2.final_backend.domain.member.command.domain.aggregate.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    Member findByLoginId(String loginId);
}
