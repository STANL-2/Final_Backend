package stanl_2.final_backend.domain.promotion.command.domain.aggregate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stanl_2.final_backend.domain.promotion.command.domain.aggregate.entity.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion,String> {
}
