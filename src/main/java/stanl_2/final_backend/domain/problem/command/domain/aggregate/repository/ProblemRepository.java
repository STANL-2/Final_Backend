package stanl_2.final_backend.domain.problem.command.domain.aggregate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stanl_2.final_backend.domain.problem.command.domain.aggregate.entity.Problem;

public interface ProblemRepository extends JpaRepository<Problem, String> {
}
