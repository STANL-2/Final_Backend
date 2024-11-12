package stanl_2.final_backend.domain.member.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stanl_2.final_backend.domain.member.command.domain.aggregate.entity.Member;

public interface AuthRepository extends JpaRepository<Member, String> {
}
