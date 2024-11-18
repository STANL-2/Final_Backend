package stanl_2.final_backend.domain.problem.command.domain.aggregate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanl_2.final_backend.domain.problem.command.domain.aggregate.entity.Problem;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, String> {
}
