package stanl_2.final_backend.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanl_2.final_backend.domain.member.aggregate.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByloginId(String loginId);
}
