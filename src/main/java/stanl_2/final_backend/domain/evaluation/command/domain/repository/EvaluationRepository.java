package stanl_2.final_backend.domain.evaluation.command.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stanl_2.final_backend.domain.evaluation.command.domain.aggregate.entity.Evaluation;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, String> {
}
